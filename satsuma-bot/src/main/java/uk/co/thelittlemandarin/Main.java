package uk.co.thelittlemandarin;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.model.event.AppHomeOpenedEvent;
import io.grpc.ManagedChannelBuilder;
import uk.co.thelittlemandarin.actions.CounterButtonActionHandler;
import uk.co.thelittlemandarin.events.SatsumaHomeEventHandler;
import uk.co.thelittlemandarin.mandarin.StockistsGrpc;
import uk.co.thelittlemandarin.mandarin.StockistsOuterClass;
import uk.co.thelittlemandarin.mandarin.auth.JwtCallCredentials;

import java.util.stream.Collectors;

import static uk.co.thelittlemandarin.mandarin.StockistsOuterClass.GetStockistsRequest;

public class Main {

    public static void main(String[] args) throws Exception {
        var app = new App();

        var channel = ManagedChannelBuilder
            .forAddress("localhost", 5001)
            .build();
        var apiKey = System.getenv("MANDARIN_API_KEY");
        var callCredentials = new JwtCallCredentials(apiKey);
        var stockists = StockistsGrpc.newBlockingStub(channel)
            .withCallCredentials(callCredentials);

        app.blockAction("buttonCounter", new CounterButtonActionHandler());

        app.event(AppHomeOpenedEvent.class, new SatsumaHomeEventHandler());

        app.command("/hello", (req, ctx) -> {
            try {
                var request = GetStockistsRequest.newBuilder().build();
                var response = stockists.getStockists(request);
                ctx.respond(response.getStockistsList()
                    .stream()
                    .map(StockistsOuterClass.Stockist::getStockistCode)
                    .collect(Collectors.joining(", ")));
                return ctx.ack("Hello");
            } catch (Exception e) {
                return ctx.ack(e.toString());
            }
        });

        var server = new SlackAppServer(app);
        server.start();
    }

}

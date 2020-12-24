package uk.co.thelittlemandarin;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.model.event.AppHomeOpenedEvent;
import uk.co.thelittlemandarin.actions.CounterButtonActionHandler;
import uk.co.thelittlemandarin.events.SatsumaHomeEventHandler;
import uk.co.thelittlemandarin.mandarin.ApiClient;
import uk.co.thelittlemandarin.mandarin.ApiException;
import uk.co.thelittlemandarin.mandarin.api.StockistsApi;
import uk.co.thelittlemandarin.mandarin.model.Stockist;

public class Main {

    public static void main(String[] args) throws Exception {
        var app = new App();

        var apiClient = new ApiClient();
        var apiKey = System.getProperty("MANDARIN_API_KEY");
        apiClient.setApiKeyPrefix("Bearer");
        apiClient.setApiKey(apiKey);
        var stockistsController = new StockistsApi(apiClient);

        app.blockAction("buttonCounter", new CounterButtonActionHandler());

        app.event(AppHomeOpenedEvent.class, new SatsumaHomeEventHandler());

        app.command("/hello", (req, ctx) -> {
            try {
                var stockist = stockistsController.stockistsGetStockistByCode("EK19");
                return ctx.ack(apiClient.getJSON().getContext(Stockist.class).writeValueAsString(stockist));
            } catch (ApiException e) {
                return ctx.ack(e.toString());
            }
        });

        var server = new SlackAppServer(app);
        server.start();
    }

}

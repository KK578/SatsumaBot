package uk.co.thelittlemandarin.commands.stockists;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import lombok.AllArgsConstructor;
import uk.co.thelittlemandarin.mandarin.StockistsGrpc;
import uk.co.thelittlemandarin.mandarin.StockistsOuterClass;

import javax.inject.Inject;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Inject))
class AllStockistsSlashCommandHandler implements SlashCommandHandler {

    private final StockistsGrpc.StockistsBlockingStub stockistsGrpc;

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext slashCommandContext) {
        try {
            var request = StockistsOuterClass.GetStockistsRequest.newBuilder().build();
            var response = stockistsGrpc.getStockists(request);
            slashCommandContext.respond(response.getStockistsList()
                .stream()
                .map(StockistsOuterClass.Stockist::getStockistCode)
                .collect(Collectors.joining(", ")));
            return slashCommandContext.ack("Hello");
        } catch (Exception e) {
            return slashCommandContext.ack(e.toString());
        }
    }

}

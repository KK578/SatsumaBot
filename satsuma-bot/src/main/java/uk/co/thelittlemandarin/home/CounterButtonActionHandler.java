package uk.co.thelittlemandarin.home;

import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.view.Views;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import java.io.IOException;

@AllArgsConstructor(onConstructor = @__(@Inject))
public class CounterButtonActionHandler implements BlockActionHandler {

    public static int count = 0;

    @Override
    public Response apply(BlockActionRequest blockActionRequest, ActionContext actionContext) throws IOException, SlackApiException {
        var view = Views.view(HomeViewConfigurer.builder().count(++count).build());
        actionContext.client().viewsPublish(r -> r.userId(actionContext.getRequestUserId()).view(view));
        return actionContext.ack();
    }

}

package com.mandarin.actions;

import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.view.Views;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

public class Button1ActionHandler implements BlockActionHandler {
    public static int count = 0;

    @Override
    public Response apply(BlockActionRequest blockActionRequest, ActionContext actionContext) throws IOException, SlackApiException {
        var view = Views.view(v -> v.type("home")
        .blocks(asBlocks(
                // Oh no the copy and paste!
                section(section -> section.text(markdownText(mt -> mt.text("*Welcome to your _App's Home_* :tada:")))),
                divider(),
                section(section -> section.text(markdownText(mt -> mt.text("This button won't do much for now but you can set up a listener for it using the `actions()` method and passing its unique `action_id`. See an example on <https://slack.dev/java-slack-sdk/guides/interactive-components|slack.dev/java-slack-sdk>.")))),
                actions(actions -> actions
                        .elements(asElements(
                                button(b -> b.text(plainText(pt -> pt.text("Click me!"))).value("button1").actionId("button_1"))
                        ))
                ),
                section(s -> s.text(markdownText(String.format("Wow the count is now %s.", ++count))))
        )));
        actionContext.client().viewsPublish(r -> r.userId(actionContext.getRequestUserId()).view(view));
        return actionContext.ack();
    }
}

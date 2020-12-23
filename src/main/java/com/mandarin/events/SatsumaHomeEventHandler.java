package com.mandarin.events;

import com.mandarin.views.HomeViewConfigurer;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.Views;

import java.io.IOException;

public class SatsumaHomeEventHandler implements BoltEventHandler<AppHomeOpenedEvent> {

    @Override
    public Response apply(EventsApiPayload<AppHomeOpenedEvent> payload, EventContext context) throws IOException, SlackApiException {
        var view = Views.view(HomeViewConfigurer.builder().build());
        context.client().viewsPublish(r -> r.userId(payload.getEvent().getUser()).view(view));
        return context.ack();
    }

}

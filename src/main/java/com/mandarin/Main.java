package com.mandarin;

import com.mandarin.actions.Button1ActionHandler;
import com.mandarin.events.SatsumaHomeEventHandler;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.model.event.AppHomeOpenedEvent;

public class Main {
    public static void main(String[] args) throws Exception {
        var app = new App();

        app.event(AppHomeOpenedEvent.class, new SatsumaHomeEventHandler());
        app.blockAction("button_1", new Button1ActionHandler());

        app.command("/hello", (req, ctx) -> {
            return ctx.ack(":wave: Hello!");
        });

        var server = new SlackAppServer(app);
        server.start();
    }
}

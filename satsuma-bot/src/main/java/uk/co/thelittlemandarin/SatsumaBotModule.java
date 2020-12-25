package uk.co.thelittlemandarin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.slack.api.bolt.App;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.jetty.SlackAppServer;

import java.util.Map;

public class SatsumaBotModule extends AbstractModule {

    @Provides
    SlackAppServer slackAppServer(App app) {
        return new SlackAppServer(app);
    }

    @Provides
    App app(Map<String, BlockActionHandler> blockActionHandlerMap,
            Map<String, SlashCommandHandler> commandHandlerMap,
            Map<Class, BoltEventHandler> eventHandlerMap) {
        var app = new App();

        blockActionHandlerMap.forEach(app::blockAction);
        commandHandlerMap.forEach(app::command);
        eventHandlerMap.forEach(app::event);

        return app;
    }

}

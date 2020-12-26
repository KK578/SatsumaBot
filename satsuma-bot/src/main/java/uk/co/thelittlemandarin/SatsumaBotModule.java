package uk.co.thelittlemandarin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.slack.api.bolt.App;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.jetty.SlackAppServer;
import uk.co.thelittlemandarin.commands.stockists.SatsumaBotStockistsCommandModule;
import uk.co.thelittlemandarin.home.SatsumaBotHomeModule;

import java.util.Map;

public class SatsumaBotModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new SatsumaBotStockistsCommandModule());
        install(new SatsumaBotHomeModule());
    }

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

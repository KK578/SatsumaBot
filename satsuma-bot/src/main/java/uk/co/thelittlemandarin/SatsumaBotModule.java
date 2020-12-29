package uk.co.thelittlemandarin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.slack.api.bolt.App;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.jetty.SlackAppServer;
import lombok.extern.slf4j.Slf4j;
import uk.co.thelittlemandarin.commands.stockists.SatsumaBotStockistsCommandModule;
import uk.co.thelittlemandarin.home.SatsumaBotHomeModule;

import java.util.Map;

@Slf4j
public class SatsumaBotModule extends AbstractModule {

    @Override
    protected void configure() {
        Names.bindProperties(binder(), System.getProperties());
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
            @SuppressWarnings("rawtypes") Map<Class, BoltEventHandler> eventHandlerMap) {
        var app = new App();

        blockActionHandlerMap.forEach(app::blockAction);
        commandHandlerMap.forEach(app::command);
        eventHandlerMap.forEach(app::event);

        return app;
    }

}

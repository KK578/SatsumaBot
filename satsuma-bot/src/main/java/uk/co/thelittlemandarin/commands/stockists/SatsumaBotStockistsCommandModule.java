package uk.co.thelittlemandarin.commands.stockists;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;

public class SatsumaBotStockistsCommandModule extends AbstractModule {

    @Override
    protected void configure() {
        var commandBinder = MapBinder.newMapBinder(binder(), String.class, SlashCommandHandler.class);
        commandBinder.addBinding("/artists").to(AllStockistsSlashCommandHandler.class);
    }

}

package uk.co.thelittlemandarin.home;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.model.event.AppHomeOpenedEvent;

public class SatsumaBotHomeModule extends AbstractModule {

    @Override
    protected void configure() {
        var eventHandlerMap = MapBinder.newMapBinder(binder(), Class.class, BoltEventHandler.class);
        eventHandlerMap.addBinding(AppHomeOpenedEvent.class).to(SatsumaHomeEventHandler.class);

        var blockActionHandlerMap = MapBinder.newMapBinder(binder(), String.class, BlockActionHandler.class);
        blockActionHandlerMap.addBinding("buttonCounter").to(CounterButtonActionHandler.class);
    }

}

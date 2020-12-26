package uk.co.thelittlemandarin;

import com.google.inject.Guice;
import com.slack.api.bolt.jetty.SlackAppServer;
import uk.co.thelittlemandarin.mandarin.MandarinApiModule;

public class Main {

    public static void main(String[] args) throws Exception {
        var injector = Guice.createInjector(
            new MandarinApiModule(),
            new SatsumaBotModule()
        );

        var server = injector.getInstance(SlackAppServer.class);
        server.start();
    }

}

package uk.co.thelittlemandarin;

import com.google.inject.Guice;
import com.slack.api.bolt.jetty.SlackAppServer;
import uk.co.thelittlemandarin.commands.stockists.SatsumaBotStockistsCommandModule;
import uk.co.thelittlemandarin.home.SatsumaBotHomeModule;
import uk.co.thelittlemandarin.mandarin.channel.MandarinGrpcChannelModule;

public class Main {

    public static void main(String[] args) throws Exception {
        var injector = Guice.createInjector(
            new SatsumaBotModule(),
            new SatsumaBotHomeModule(),
            new SatsumaBotStockistsCommandModule(),
            new MandarinGrpcChannelModule()
        );

        var server = injector.getInstance(SlackAppServer.class);
        server.start();
    }

}

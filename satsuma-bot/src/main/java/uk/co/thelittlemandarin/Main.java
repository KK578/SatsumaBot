package uk.co.thelittlemandarin;

import com.google.inject.Guice;
import com.slack.api.bolt.jetty.SlackAppServer;
import lombok.extern.slf4j.Slf4j;
import uk.co.thelittlemandarin.mandarin.MandarinApiModule;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            loadProperties();

            var injector = Guice.createInjector(
                new MandarinApiModule(),
                new SatsumaBotModule()
            );

            var server = injector.getInstance(SlackAppServer.class);
            server.start();
        } catch (Exception ex) {
            log.error("Fatal error during application start-up.", ex);
        }
    }

    private static void loadProperties() {
        var properties = new Properties();
        properties.putAll(System.getProperties());
        properties.putAll(getConfigProperties());

        System.setProperties(properties);
    }


    private static Properties getConfigProperties() {
        var properties = new Properties();

        try {
            var propertiesPath = Thread.currentThread().getContextClassLoader().getResource("config/satsuma-bot.properties");
            if (Objects.nonNull(propertiesPath))
                properties.load(new FileInputStream(propertiesPath.getPath()));
        } catch (IOException e) {
            log.error("Error attempting to read satsuma-bot.properties.", e);
        }

        return properties;
    }

}

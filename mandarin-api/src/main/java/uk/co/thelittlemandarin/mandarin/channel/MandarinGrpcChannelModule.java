package uk.co.thelittlemandarin.mandarin.channel;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import uk.co.thelittlemandarin.mandarin.MandarinApi;
import uk.co.thelittlemandarin.mandarin.StockistsGrpc;

public class MandarinGrpcChannelModule extends AbstractModule {

    @Provides
    StockistsGrpc.StockistsBlockingStub blockingStockistsStub(@MandarinApi ManagedChannel channel, @MandarinApi JwtCallCredentials callCredentials) {
        return StockistsGrpc.newBlockingStub(channel)
            .withCallCredentials(callCredentials);
    }

    @Provides
    @MandarinApi
    ManagedChannel mandarinApiChannel() {
        return ManagedChannelBuilder
            .forAddress("localhost", 5001)
            .enableRetry()
            .build();
    }

    @Provides
    @MandarinApi
    JwtCallCredentials jwtCallCredentials() {
        var apiKey = System.getenv("MANDARIN_API_KEY");
        return new JwtCallCredentials(apiKey);
    }

}

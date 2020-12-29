package uk.co.thelittlemandarin.mandarin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.inject.Singleton;

public class MandarinApiModule extends AbstractModule {

    @Provides
    StockistsGrpc.StockistsBlockingStub blockingStockistsStub(
        @MandarinApi ManagedChannel channel,
        @MandarinApi JwtCallCredentials callCredentials) {
        return StockistsGrpc.newBlockingStub(channel)
            .withCallCredentials(callCredentials);
    }

    @Provides
    @MandarinApi
    @Singleton
    ManagedChannel mandarinApiChannel(MandarinApiProperties properties) {
        return ManagedChannelBuilder
            .forAddress(properties.getHost(), properties.getPort())
            .enableRetry()
            .build();
    }

    @Provides
    @MandarinApi
    JwtCallCredentials jwtCallCredentials(MandarinApiProperties properties) {
        return new JwtCallCredentials(properties);
    }

}

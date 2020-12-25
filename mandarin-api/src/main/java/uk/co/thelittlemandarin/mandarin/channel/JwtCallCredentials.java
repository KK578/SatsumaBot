package uk.co.thelittlemandarin.mandarin.channel;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import lombok.AllArgsConstructor;

import java.util.concurrent.Executor;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@AllArgsConstructor
class JwtCallCredentials extends CallCredentials {

    private final String token;

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                headers.put(Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER), String.format("Bearer %s", token));
                metadataApplier.apply(headers);
            } catch (Throwable e) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {

    }

}

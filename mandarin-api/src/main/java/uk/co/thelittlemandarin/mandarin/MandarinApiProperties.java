package uk.co.thelittlemandarin.mandarin;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.inject.Inject;
import javax.inject.Named;

@Value
@AllArgsConstructor(onConstructor = @__(@Inject))
class MandarinApiProperties {

    @Named("mandarin.host") String host;
    @Named("mandarin.port") int port;
    @Named("mandarin.jwtToken") String jwtToken;

}

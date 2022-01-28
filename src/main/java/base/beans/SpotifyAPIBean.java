package base.beans;

import base.utils.CheckLocal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@Service
public class SpotifyAPIBean {
    private static final String clientId = "0fb5df0740314db9a59d826b3c46a53c";
    private static final String clientSecret  = "ceb2fc210c3448f4aa78fc07a7ca3394";
    private static final URI redirectUri = SpotifyHttpManager.makeUri (CheckLocal.checkIsLocal() ? "http://localhost:8080/login" : "http://3.65.240.94:8080/login");

    @Bean
    @Scope("prototype")
    public SpotifyApi init(){

        return new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }
}

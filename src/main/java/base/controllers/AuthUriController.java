package base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

@RestController
public class AuthUriController {

    @Autowired SpotifyApi spotifyApi;

    @GetMapping("/auth")
    public String GetLoginURI(){
        System.out.println("Function: Auth");

        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("user-library-read,user-read-email").build();
        URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }



}

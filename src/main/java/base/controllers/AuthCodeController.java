package base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import base.utils.CheckLocal;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthCodeController {
    @Autowired
    SpotifyApi spotifyApi;

    @GetMapping("/login")
    public void DoLogin(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        System.out.println("Function: Login");
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error in /login: " + e.getMessage());
        }
        System.out.println(spotifyApi.getAccessToken());

        String redirectURL = CheckLocal.checkIsLocal() ? "http://localhost:3000/login?access_token=" : "http://3.65.240.94:3000/login?access_token=";
        response.sendRedirect(redirectURL+spotifyApi.getAccessToken());
    }
}

//        ResponseCookie resCookie = ResponseCookie.from("access_token", code)
////                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(-1)
////                .maxAge(1 * 24 * 60 * 60) # default is session cookie
//                .domain("localhost")
//                .build();
//        String redirectURL = "redirect:http://localhost:3000/";
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.LOCATION, redirectURL)
//                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
//                .build();

package archive;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class TopArtistsController {

    @Autowired SpotifyApi spotifyApi;

    @GetMapping("/top-artists")
    public List<Artist> getTopTrack(@CookieValue("access_token") String access_token){
        Paging<Artist> topArtists = null;
        spotifyApi.setAccessToken(access_token);
        try {
            topArtists = spotifyApi.getUsersTopArtists().limit(10).time_range("medium_term").build().execute();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
            System.out.println("Error in search track"+ e);
        }
        return Arrays.stream(topArtists.getItems()).toList();
    }
}

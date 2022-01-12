package archive;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class SearchController {

    @Autowired SpotifyApi spotifyApi;

    private static List<HashMap<String,String>> getTrackMap(SearchResult tracks){
        List<HashMap<String,String>> tracksData = new ArrayList<>();
        // check if tracks is null
        for (Track track : tracks.getTracks().getItems() ){
            HashMap<String,String> trackData = new HashMap<>();
            trackData.put("Name",track.getName());
            trackData.put("Id",track.getId());
            trackData.put("PreviewUrl",track.getPreviewUrl());
            trackData.put("Artists",track.getArtists()[0].getName());
            tracksData.add(trackData);
        }
        return tracksData;
    }

    @GetMapping("/search/track")
    public List<HashMap<String, String>> getTrack(@RequestParam("query") String query, @CookieValue("access_token") String access_token){
        SearchResult tracks = null;
        spotifyApi.setAccessToken(access_token);
        try {
            tracks = spotifyApi.searchItem(query,"track").limit(10).build().execute();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
            System.out.println("Error in search track"+ e);
        }
//        try{
//            System.out.println(spotifyApi.getCurrentUsersProfile().build().execute().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (SpotifyWebApiException e) {
//            e.printStackTrace();
//        }
        return getTrackMap(tracks);

    }
}

package base.controllers;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class SavedTracksController {

    @Autowired SpotifyApi spotifyApi;

    public Paging<SavedTrack> getTracks(String access_token, int limit, int offset){
        spotifyApi.setAccessToken(access_token);
        Paging<SavedTrack> savedTracks = null;
        List<HashMap<String,String>> tracksList = new ArrayList<>();
        try {
            savedTracks = spotifyApi.getUsersSavedTracks().limit(limit).offset(offset).build().execute();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
            System.out.println("Error in search track" + e);
        }
        return savedTracks;
    }

    @GetMapping("/saved-tracks")
    public List<HashMap<String, String>> endpoint(@CookieValue("access_token") String access_token){
        spotifyApi.setAccessToken(access_token);
        List<HashMap<String,String>> tracksList = new ArrayList<>();
        int offset = 0;

        var savedTracks = getTracks(access_token,50,offset);
        while (savedTracks.getNext() != null) {
            for (var savedTrack:savedTracks.getItems()) {
                HashMap<String, String> trackInfo = new HashMap<>();
                trackInfo.put("addedAt", savedTrack.getAddedAt().toString());
                var track = savedTrack.getTrack();
                trackInfo.put("name", track.getName());
                trackInfo.put("artist", track.getArtists()[0].getName());
                trackInfo.put("uri", track.getUri());
                trackInfo.put("id", track.getUri());
                trackInfo.put("preview_url", track.getPreviewUrl());
                tracksList.add(trackInfo);
            }
            offset = offset + 50;
            savedTracks = getTracks(access_token,50,offset);
        }
        System.out.println("Returned tracks: "+tracksList.size());
        return tracksList;
    }
}

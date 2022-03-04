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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class SavedTracksController {

    @Autowired SpotifyApi spotifyApi;

    public List<SavedTrack> getTracksAll(String access_token) {
        spotifyApi.setAccessToken(access_token);
        var requestBuilder = spotifyApi.getUsersSavedTracks().limit(50);
        List<SavedTrack> tracksList = new ArrayList<>() ;
        CompletableFuture<Paging<SavedTrack>> tracksPager;
        int total = 0;
        do {
            tracksPager = requestBuilder.build().executeAsync();
            try {
                total = tracksPager.get().getTotal();
                tracksList.addAll(List.of(tracksPager.get().getItems()));
                requestBuilder.offset(tracksPager.get().getOffset() + 50);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.out.println("Error in search track" + e);
            }
        } while (tracksList.size() < total);
        System.out.println(tracksList.size());
        return tracksList;
    }

    @GetMapping("/saved-tracks")
    public List<HashMap<String, String>> endpoint(@CookieValue("access_token") String access_token){
        spotifyApi.setAccessToken(access_token);
        List<HashMap<String,String>> tracksList = new ArrayList<>();
        int offset = 0;

        var savedTracks = getTracksAll(access_token);
        for (var savedTrack:savedTracks) {
            HashMap<String, String> trackInfo = new HashMap<>();
            trackInfo.put("addedAt", String.valueOf(savedTrack.getAddedAt().getTime()));
            var track = savedTrack.getTrack();
            trackInfo.put("name", track.getName());
            trackInfo.put("artist", track.getArtists()[0].getName());
            trackInfo.put("uri", track.getUri());
            trackInfo.put("id", track.getUri());
            trackInfo.put("preview_url", track.getPreviewUrl());
            trackInfo.put("albumArt",track.getAlbum().getImages()[0].getUrl());
            tracksList.add(trackInfo);
        }
        System.out.println("Returned tracks: "+tracksList.size());
        return tracksList;
    }
}

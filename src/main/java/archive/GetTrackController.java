package archive;//package base.controllers;
//
//import org.apache.hc.core5.http.ParseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import se.michaelthelin.spotify.SpotifyApi;
//import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
//import se.michaelthelin.spotify.model_objects.specification.Track;
//
//import java.io.IOException;
//
//@RestController
//public class GetTrackController {
//
//    @Autowired SpotifyApi spotifyApi;
//
//    @GetMapping("/track")
//    public Track getTrack(@RequestParam("songID") String songID){
////        final var songID = "0fYVliAYKHuPmECRs1pbRf";
//        Track song = null;
//        try {
//            song = spotifyApi.getTrack(songID).build().execute();
//        } catch (SpotifyWebApiException | IOException | ParseException e){
//            System.out.println("Error" + e.getMessage());
//        }
//        return song;
//    }
//}

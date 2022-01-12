package archive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetCookieTest {
    @GetMapping("/create-cookie")
    public ResponseEntity setCookie() {

        ResponseCookie resCookie = ResponseCookie.from("sample", "sample123")
                .httpOnly(true)
                .secure(true)
                .path("/")
//                .maxAge(1 * 24 * 60 * 60) # default is session cookie
                .domain("localhost")
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resCookie.toString()).build();

    }

}

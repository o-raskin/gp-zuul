package ru.olegraskin.gpzuul.auth2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping
public class SessionController {

    @GetMapping("sessions/me")
    public ResponseEntity<?> user(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "unauthorized"));
            // dat not me!!!!!!! TODO should be custom exception handler
        }

        return ResponseEntity.ok(principal);
    }

    @GetMapping("sessions/google/callback")
    public void githubCallback(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:4040/home");
    }
}

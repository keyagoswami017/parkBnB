package edu.neu.csye6200.parkingapp.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    private String secretKey = "1wZ!v#tQpRj7ZB8h3F*sNcL9$Hk0B0g@5Wv"; // your plain secret key

    public String encodeSecretKey(String secret) {
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }
    String encodedSecret = encodeSecretKey(secretKey);
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(encodedSecret)) // Decode and use Base64 secret
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}

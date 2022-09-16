package uz.marta.weather.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.marta.weather.ref.UserRole;

import java.util.Date;

@Component
public class JwtProvider {

    private static final long expireTime = 36_000_000;
    private static final String secretKey = "ThisIsSecretKey1234";

    public static String generateToken(String phoneNumber, UserRole role) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", role.name())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return token;
    }


    public String getUsernameFromToken(String token) {
        try {
            String username = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;
        } catch (Exception e) {
            return null;
        }

    }

}

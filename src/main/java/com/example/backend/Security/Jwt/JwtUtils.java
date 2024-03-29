package com.example.backend.Security.Jwt;

import com.example.backend.Service.AdminDetailsImpl;
import com.example.backend.Service.BlacklistService;
import com.example.backend.Service.DoctorDetailsImpl;
import com.example.backend.Service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    BlacklistService blacklistService;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${had.app.jwtSecret}")
    private String jwtSecret;

    @Value("${had.app.jwtExpirationMs}")
    private Integer jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        if(authentication.getPrincipal().toString().matches(".*UserDetailsImpl.*")){
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

            return generateTokenFromUsername(userPrincipal.getUsername());
        }
        else if(authentication.getPrincipal().toString().matches(".*DoctorDetailsImpl.*")){
            DoctorDetailsImpl doctorPrincipal = (DoctorDetailsImpl) authentication.getPrincipal();

            return generateTokenFromUsername(doctorPrincipal.getUsername());
        }
        else{
            AdminDetailsImpl adminPrincipal = (AdminDetailsImpl) authentication.getPrincipal();

            return generateTokenFromUsername(adminPrincipal.getUsername());
        }
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            if(blacklistService.isTokenAvailable(authToken)){
                System.out.println(("javsdjhdasvavdajdvasvjdasvhjdasvjd"));
                throw new MalformedJwtException("Invalid");
            }
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
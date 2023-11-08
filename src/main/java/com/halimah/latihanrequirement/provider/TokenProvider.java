package com.halimah.latihanrequirement.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.halimah.latihanrequirement.domain.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;


@Component
public class TokenProvider {
    public static final String AUTHORITIES = "authorities";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    private static final String GET_ARRAYS_LLC = "GET_ARRAYS_LLC";
    private static final String CUSTOMER_MANAGEMENT_SERVICE = "CUSTOMER_MANAGEMENT_SERVICE";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1_800_000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 432_000_000;
    @Value("${jwt.secret}")
    private String secret;

    public String createAccessToken(UserPrincipal userPrincipal) {
        return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(CUSTOMER_MANAGEMENT_SERVICE)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername()).withArrayClaim(AUTHORITIES, getClaimsFromUser(userPrincipal))
                .withExpiresAt(new Date(currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));

    }

    public String createRefreshToken(UserPrincipal userPrincipal) {
        return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(CUSTOMER_MANAGEMENT_SERVICE)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));

    }

    public String getSubject(String token, HttpServletRequest request) {
        try {
            return getJWTVerifier().verify(token).getSubject();
        } catch (TokenExpiredException exception) {
            request.setAttribute("expiredMessage", exception.getMessage());
            throw exception;
        } catch (InvalidClaimException exception) {
            request.setAttribute("invalidClaim", exception.getMessage());
            throw exception;

        } catch (Exception exception) {
            throw exception;
        }
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(toList());
    }

    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public boolean isTokenValid(String email, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(email) && !isTokenExpired(verifier, token);
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }
}

//@Component
//public class TokenProvider {
//    public static final String GET_ARRAYS_LLC = "GET_ALWAYS_LLC";
//    public static final String CUSTOMER_MANGEMENT_SERVICE = "CUSTOMER_MANGEMENT_SERVICE";
//    public static final String AUTHORITIES = "authorities";
//    public static final Long ACCES_TOKEN_EXPURATION_TIME = 1_800_000L;
//    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 432_000_009L;
//    public static final String TOKEN_CANNOT_BE_VERIFIED = "token can not verified";
//    @Value("${jwt.secret}")
//    private String secret;
//
//    public String createAccessToken(UserPrincipal userPrincipal){
//        return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(CUSTOMER_MANGEMENT_SERVICE)
//                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername()).withArrayClaim(AUTHORITIES,getClaimsFromUser(userPrincipal))
//                .withExpiresAt(new Date(currentTimeMillis() + ACCES_TOKEN_EXPURATION_TIME))
//                .sign(HMAC512(secret.getBytes()));
//    }
//    private String[] getClaimsFromUser(UserPrincipal userPrincipal){
//        return  userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
//    }
//    public String createRefreshToken(UserPrincipal userPrincipal){
//        return JWT.create().withIssuer(GET_ARRAYS_LLC).withAudience(CUSTOMER_MANGEMENT_SERVICE)
//                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
//                .withExpiresAt(new Date(currentTimeMillis() + ACCES_TOKEN_EXPURATION_TIME))
//                .sign(HMAC512(secret.getBytes()));
//    }
//    public List<GrantedAuthority> getAuthorities(String token){
//        String[] claims = getClaimsFromToken(token);
//        return stream(claims).map(SimpleGrantedAuthority::new).collect(toList());
//    }
//    private String[] getClaimsFromToken(String token){
//        JWTVerifier verifier = getJWTVerifier();
//        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
//    }
//
//    private JWTVerifier getJWTVerifier() {
//        JWTVerifier verifier;
//        try {
//            Algorithm algorithm = HMAC512(secret);
//            verifier = JWT.require(algorithm).withIssuer(GET_ARRAYS_LLC).build();
//        }catch (JWTVerificationException exception){
//            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
//
//        }return verifier;
//    }
//    private boolean isTokenExpired(JWTVerifier verifier, String token){
//        Date expiration = verifier.verify(token).getExpiresAt();
//        return expiration.before(new Date());
//    }
//    public String getSubject(String token, HttpServletRequest request){
//        try {
//            return getJWTVerifier().verify(token).getSubject();
//        }catch (TokenExpiredException exception){
//            request.setAttribute("expiredMassage",exception.getMessage());
//            throw exception;
//        }catch (InvalidClaimException exception){
//            request.setAttribute("invalidClaim",exception.getMessage());
//            throw exception;
//        }catch (Exception exception){
//            throw exception;
//        }
//    }
//    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities,HttpServletRequest request){
//        UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(email,null,authorities);
//        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        return  userPasswordAuthToken;
//    }
//    public boolean isTokenvalid(String email, String token){
//        JWTVerifier verifier = getJWTVerifier();
//        return StringUtils.isNotEmpty(email) && !isTokenExpired(verifier,token);
//    }
//}

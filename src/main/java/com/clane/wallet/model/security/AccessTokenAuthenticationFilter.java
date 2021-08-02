package com.clane.wallet.model.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author timolor
 * @created 28/07/2021
 */
public class AccessTokenAuthenticationFilter  extends OncePerRequestFilter {

    @Value("${security.oauth2.resource.jwt.key-value}")
    private String publicKeyString;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = header.replace("Bearer","");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getPublicKey())
                    .parseClaimsJws(token)
                    .getBody();

            String username = (String) claims.get("username");

            if(username != null ){
                List<String> authorities = (List<String>) claims.get("authorities");
                Collection<? extends GrantedAuthority> grantedAuthorities = Objects.isNull(authorities )|| authorities.isEmpty()?null:authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsY9wY6rwdql0GCPFxu7eEypLC8TkDQHgaFlmP5QBf7Il4/8/ogTrT1cLF/fMrfhoJrPeZWjzP6NmaxttCUcki8JSbmaxdYNnIPHsPFvqr1DlpvikdKiG5lkKS27E9uUQ1XmeXh9Vhn9QwLQyXl5bbbjlDewiCzB/MuKlbuuxFsb7ZPpBD+rE69efUfTu8dZPa5QzTugqxqAe9q2soLPxYyh91BGjVGWMN9wwWnM+WaQ9IehuQko7drlvJRVhPow9zvzTanaJckn02+Ubj5+LFQ8yYNe/PQQSS+noaQtmCqRfVhL161F6yivj16EuYJhkUn3TQQTbDhSU3CcdMcX5lQIDAQAB";
        byte[] keyBytes = Base64.getMimeDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(spec);
    }
}

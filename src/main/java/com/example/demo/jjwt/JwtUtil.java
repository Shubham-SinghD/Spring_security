package com.example.demo.jjwt;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	@Value("${spring.jpa.properties.jwt.secret}")
	private String secretKey1="";

	public JwtUtil()  {
		try {
			KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey = keyGen.generateKey();
			secretKey1 = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	 private  SecretKey getKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(secretKey1);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
	 
	 public  String generateToken(String username) {
	    	Map<String, Object>mp=new HashMap<String, Object>();
	        return Jwts.builder()
	        		.claims()
	        		.add(mp)
	                .subject(username)
	                .issuedAt(new Date())
	                .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) 
	                .and()
	                .signWith(getKey())
	                .compact();
	    }
	// Extract Name
	 public  String extractUsername(String token) {
	        return extractClaims(token).getSubject();
	    }
	    // Validation token
	    public  boolean valideToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
		// Token Expiration'
		private  boolean isTokenExpired(String token) {
	        return extractClaims(token).getExpiration().before(new Date());
	    }
		public  Claims extractClaims(String token) {
			return Jwts.parser()
					.verifyWith(getKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();
					
		}
		

}
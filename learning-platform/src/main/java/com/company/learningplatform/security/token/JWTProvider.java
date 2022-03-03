package com.company.learningplatform.security.token;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.company.learningplatform.constant.MessageEnum;
import com.company.learningplatform.constant.SecurityConstant;
import com.company.learningplatform.security.UserPrincipal;

@Component
public class JWTProvider
{
	@Value("${app.jwtSecret}")
	private String secret;

	public String generateToken(UserPrincipal userPrincipal)
	{
		String jwt = JWT.create()
				.withIssuer(SecurityConstant.ISSUER)
				.withIssuedAt(new Date())
				.withSubject(userPrincipal.getUsername())
				.withArrayClaim(SecurityConstant.AUTHORITIES,
						userPrincipal.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList())
								.toArray(new String[0]))
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(secret.getBytes()));

		return jwt;
	}

	public String tokenForEmailConfirm(String email)
	{
		String jwt = JWT.create()
				.withIssuer(SecurityConstant.ISSUER)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.FIRST_LOGIN_EXPIRATION_TIME))
				.withSubject(email)
				.sign(Algorithm.HMAC512(secret.getBytes()));

		return jwt;
	}

	public String getSubject(String token)
	{
		return getJWTVerifier().verify(token).getSubject();
	}

	public boolean isTokenValid(String username, String token)
	{
		return StringUtils.isNotEmpty(username)
				&& !isTokenExpired(getJWTVerifier(), token);
	}

	public Set<GrantedAuthority> getAuthorities(String token)
	{
		return Arrays.stream(getClaimsFromToken(token))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	private String[] getClaimsFromToken(String token)
	{
		return getJWTVerifier().verify(token)
				.getClaim(SecurityConstant.AUTHORITIES)
				.asArray(String.class);
	}

	public Authentication getAuthentication(String username, Set<GrantedAuthority> authorities,
			HttpServletRequest request)
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, null, authorities);
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		return usernamePasswordAuthenticationToken;
	}

	private JWTVerifier getJWTVerifier()
	{
		try {
			return JWT.require(Algorithm.HMAC512(secret.getBytes()))
					.withIssuer(SecurityConstant.ISSUER)
					.build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(MessageEnum.TOKEN_CANNOT_BE_VERIFIED.getMessage());
		}
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token)
	{
		return verifier
				.verify(token).getExpiresAt()
				.before(new Date());
	}
}

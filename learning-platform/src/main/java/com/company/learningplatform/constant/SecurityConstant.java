package com.company.learningplatform.constant;

public class SecurityConstant
{
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";
	public static final long EXPIRATION_TIME = 1_000_000 * 60; // 60sec
	public static final long FIRST_LOGIN_EXPIRATION_TIME = 1_000_000 * 60 * 60 * 24;// 24h
	public static final String ISSUER = "Issuer";
	public static final String AUTHORITIES = "Authorities";
	public static final Object OPTIONS_HTTP_METHOD = "OPTIONS";

	public static final String[] PUBLIC_URLS = { "/user/login", "/user/register", "user/resetpassword/**",
			"/users/**", "/h2-console/**" };

}

package com.company.learningplatform.constant;

public class SecurityConstant
{
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "Token";
	public static final long EXPIRATION_TIME = 1_000L * 60L * 60L * 100L; // 100h
	public static final long FIRST_LOGIN_EXPIRATION_TIME = 1_000L * 60L * 60L * 24L;// 24h
	public static final String ISSUER = "Issuer";
	public static final String AUTHORITIES = "Authorities";
	public static final Object OPTIONS_HTTP_METHOD = "OPTIONS";

	public static final String[] PUBLIC_URLS = { "/users/login", "/users/confirmation", "/h2-console/**" };

}

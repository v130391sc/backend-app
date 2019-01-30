package es.upm.fi.catering.service.backendapp.security;

public class SecurityConstants {

	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/v1/clients/signUp";
	public static final String PRODUCTS_URL ="/v1/products";
	public static final String CLIENTS_URL ="/v1/clients";
}

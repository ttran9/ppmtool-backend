package tran.example.ppmtool.constants.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET = System.getenv("JWT_PRIV_KEY "); // TODO: will refactor this with a more secure implementation
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 2700000; // in milliseconds so 2700 seconds / 45 mins.
//    public static final long EXPIRATION_TIME = 300000;

}

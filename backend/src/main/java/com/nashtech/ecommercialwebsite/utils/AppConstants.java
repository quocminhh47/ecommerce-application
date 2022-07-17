package com.nashtech.ecommercialwebsite.utils;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "12";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    public static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api-docs",
            "swagger-ui.html"
            // other public endpoints of your API may be appended to this array
    };
    public static final String DEFAULT_URL = " https://res.cloudinary.com/duoih0eqa/image/upload/v1657297251/Untitled_xq9j9u.png";

    public static final int BILL_STATUS_UNSOLVED = 0;
    public static final int BILL_STATUS_ACCEPTED = 1;
    public static final int BILL_STATUS_PURCHASED = 2;
    public static final int BILL_STATUS_CANCELED = -1;

    public static final String EMAIL = "quocminhh47@gmail.com";

}

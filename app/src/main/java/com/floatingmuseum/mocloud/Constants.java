package com.floatingmuseum.mocloud;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
public interface Constants {
    String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    /**
     * Status Code
     */
    int STATUS_CODE_SUCCESS = 200;//
    int STATUS_CODE_SUCCESS_POST = 201;//new resource created (POST)
    int STATUS_CODE_SUCCESS_DELETE = 204;//no content to return (DELETE)
    int STATUS_CODE_BAD_REQUEST = 400;//request couldn't be parsed
    int STATUS_CODE_UNAUTHORISED = 401;//OAuth must be provided
    int STATUS_CODE_FORBIDDEN = 403;//invalid API key or unapproved app
    int STATUS_CODE_NOT_FOUND = 404;//method exists, but no record found
    int STATUS_CODE_METHOD_NOT_FOUND = 405;//method doesn't exist
    int STATUS_CODE_METHOD_CONFLICT = 409;//resource already created
    int STATUS_CODE_PRECONDITION_FAILED = 412;//use application/json content type
    int STATUS_CODE_UNPROCESSIBLE_ENTITY = 422;//validation errors
    int STATUS_CODE_RATE_LIMIT_EXCEEDED = 429;
    int STATUS_CODE_SERVER_ERROR = 500;
    int STATUS_CODE_SERVICE_UNAVAILABLE1 = 503;//server overloaded (try again in 30s)
    int STATUS_CODE_SERVICE_UNAVAILABLE2 = 504;//server overloaded (try again in 30s)
    int STATUS_CODE_SERVICE_UNAVAILABLE3 = 520;//Cloudflare error
    int STATUS_CODE_SERVICE_UNAVAILABLE4 = 521;//Cloudflare error
    int STATUS_CODE_SERVICE_UNAVAILABLE5 = 522;//Cloudflare error
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mounia.tasks;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jelle
 */
public class GoogleAuthHelper {
    private static String CLIENT_ID = "89787419613-ncg83ikah6n4a1pj8to8rmvpadijbcm3.apps.googleusercontent.com";
    private static String CLIENT_SECRET = "XInzMnA9scEsXQXv8HIdVMj3";
    private static final String CALLBACK_URI = "http://cloudcomputinghu.appspot.com/external";
    private static final List<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private String stateToken;
    private final GoogleAuthorizationCodeFlow flow;
    public GoogleAuthHelper() {
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).build();
        generateStateToken();
    }
    public String buildLoginUrl() {
        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
    }
    private void generateStateToken(){
        SecureRandom sr1 = new SecureRandom();
        stateToken = "google;"+sr1.nextInt();
    }
    public String getStateToken(){
        return stateToken;
    }
    public String getUserInfoJson(final String authCode) throws IOException {
        final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
        final Credential credential = flow.createAndStoreCredential(response, null);
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
        final GenericUrl url = new GenericUrl(USER_INFO_URL);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");
        final String jsonIdentity = request.execute().parseAsString();
        return jsonIdentity;
    }
}

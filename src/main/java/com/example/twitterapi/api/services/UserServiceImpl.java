package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.*;
import com.example.twitterapi.api.bean.twitter.ErrorResponseUser;
import com.example.twitterapi.api.bean.twitter.User;
import com.example.twitterapi.api.command.Command;
import com.example.twitterapi.api.command.CommandUrl;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class UserServiceImpl implements UserService {

    private final Command command;
    public static final ResponseEntity<ApiResponse> DEFAULT_ERROR_RESPONSE = new ResponseEntity<>(
            new ApiResponse(false, Collections.singletonList(new ApiError("Error inesperado.")),
                    HttpStatus.SC_INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);

    @Autowired
    public UserServiceImpl(Command command) {
        this.command = command;
    }

    @Override
    public ResponseEntity<ApiResponse> getLast10TweetsByUser(String user) throws IOException, JSONException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastTweets = new HttpGet(String.format(CommandUrl.USER_TWEETS, userTw.getId()) + "?max_results=10");
        String lastTweetsResponse = command.executeHttpMethod(getLastTweets);
        List<ApiData> apiTweetResponseList = new ArrayList<>();

        try {
            JSONArray tweetsResponseJson = new JSONObject(lastTweetsResponse).getJSONArray("data");
            for (int i = 0; i < tweetsResponseJson.length(); i++) {
                JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
                apiTweetResponseList.add(new ApiTweetResponse(tweetJson.getString("id"), tweetJson.getString("text")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }
        return new ResponseEntity<>(new ApiResponse(true, apiTweetResponseList, HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getLast10FollowersAsc(String user) throws IOException, JSONException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastFollowers = new HttpGet(String.format(CommandUrl.USER_FOLLOWERS, userTw.getId()) + "?max_results=10");
        String lastFollowersResponse = command.executeHttpMethod(getLastFollowers);
        List<ApiFollowResponse> apiFollowResponseList = new ArrayList<>();

        JSONArray tweetsResponseJson = new JSONObject(lastFollowersResponse).getJSONArray("data");
        for (int i = 0; i < tweetsResponseJson.length(); i++) {
            JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
            apiFollowResponseList.add(new ApiFollowResponse(tweetJson.getString("name"), tweetJson.getString("username")));
        }
        apiFollowResponseList.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        return new ResponseEntity<>(new ApiResponse(true, new ArrayList<>(apiFollowResponseList), HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getLastLikes(String user) throws IOException, JSONException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastLikes = new HttpGet(String.format(CommandUrl.USER_LIKED_TWEET, userTw.getId()) + "?max_results=10");
        String lastLikesResponse = command.executeHttpMethod(getLastLikes);
        List<ApiData> apiLikesResponseList = new ArrayList<>();

        JSONArray tweetsResponseJson = new JSONObject(lastLikesResponse).getJSONArray("data");
        for (int i = 0; i < tweetsResponseJson.length(); i++) {
            JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
            apiLikesResponseList.add(new ApiTweetResponse(tweetJson.getString("id"), tweetJson.getString("text")));
        }

        return new ResponseEntity<>(new ApiResponse(true, apiLikesResponseList, HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getLastMentions(String user) throws JSONException, IOException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastRetweeted = new HttpGet(String.format(CommandUrl.USER_MENTIONS, userTw.getId()) + "?max_results=10");
        String lastRetweetedResponse = command.executeHttpMethod(getLastRetweeted);
        List<ApiData> apiRetweetedResponseList = new ArrayList<>();

        JSONArray tweetsResponseJson = new JSONObject(lastRetweetedResponse).getJSONArray("data");
        for (int i = 0; i < tweetsResponseJson.length(); i++) {
            JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
            apiRetweetedResponseList.add(new ApiTweetResponse(tweetJson.getString("id"), tweetJson.getString("text")));
        }

        return new ResponseEntity<>(new ApiResponse(true, apiRetweetedResponseList, HttpStatus.SC_OK), OK);
    }

    private ResponseEntity<ApiResponse> checkUserErrors(JSONObject twUserJson, String user) {
        try {

            if (twUserJson.has("errors")) {
                JSONArray errorsJsonArray = twUserJson.getJSONArray("errors");
                ErrorResponseUser errorResponseUser = new Gson().fromJson(errorsJsonArray.getString(0), ErrorResponseUser.class);
                if (errorResponseUser.getTitle().equalsIgnoreCase("Not Found Error")) {
                    ApiResponse apiResponse = new ApiResponse(false,
                            Collections.singletonList(new ApiError(String.format("Usuario %s no encontrado.", user))),
                            HttpStatus.SC_NOT_FOUND);
                    return new ResponseEntity<>(apiResponse, NOT_FOUND);
                } else {
                    return DEFAULT_ERROR_RESPONSE;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }

        return null;
    }
}


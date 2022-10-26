package com.example.twitterapi.api.command;

public class CommandUrl {

    public static final String BASE_URL = "https://api.twitter.com/2";
    public static final String SEARCH_USERS = BASE_URL + "/users/by/username/%s";
    public static final String USER_TWEETS = BASE_URL + "/users/%s/tweets";
    public static final String USER_FOLLOWERS = BASE_URL + "/users/%s/followers";
    public static final String USER_LIKED_TWEET = BASE_URL + "/users/%s/liked_tweets";
    public static final String USER_MENTIONS = BASE_URL + "/users/%s/mentions";
}

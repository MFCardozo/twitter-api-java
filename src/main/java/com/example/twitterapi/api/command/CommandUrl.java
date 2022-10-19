package com.example.twitterapi.api.command;

public class CommandUrl {

    public static final String BASE_URL =  "https://api.twitter.com/2";
    public static final  String SEARCH_USERS = BASE_URL + "/users/by?usernames=%s";
    public static final  String USER_TWEETS = BASE_URL + "/users/%s/tweets";
}

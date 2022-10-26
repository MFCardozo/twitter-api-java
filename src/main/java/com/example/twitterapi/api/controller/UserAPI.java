package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.services.UserService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserAPI {

    private final UserService userService;

    @Autowired
    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/tweets/{user}")
    public ResponseEntity<ApiResponse> getLast10TweetsByUser(@PathVariable String user) throws IOException, JSONException {
        return userService.getLast10TweetsByUser(user);
    }

    @GetMapping("/followers_asc/{user}")
    public ResponseEntity<ApiResponse> getLast10FollowersAsc(@PathVariable String user) throws IOException, JSONException {
        return userService.getLast10FollowersAsc(user);
    }

    @GetMapping("/likes/{user}")
    public ResponseEntity<ApiResponse> lastLikes(@PathVariable String user) throws IOException, JSONException {
        return userService.getLastLikes(user);
    }

    @GetMapping("/mentions/{user}")
    public ResponseEntity<ApiResponse> lastMentions(@PathVariable String user) throws IOException, JSONException {
        return userService.getLastMentions(user);
    }
}


package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.command.Command;
import com.example.twitterapi.api.command.CommandUrl;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserAPI {

    private final Command command;

    @Autowired
    public UserAPI(Command command) {
        this.command = command;
    }

    @GetMapping("/last_tweets/{user}")
    public String hello(@PathVariable String user) throws IOException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        HttpGet getTestTw = new HttpGet(String.format(CommandUrl.USER_TWEETS, user) + "?max_results=10");
//        {"data":[{"id":"169686021","name":"ye","username":"kanyewest"}]}
        String getMenuDataResponse = command.executeHttpMethod(getTestTw);
        // TODO: implementar api
        return getMenuDataResponse;
    }
}

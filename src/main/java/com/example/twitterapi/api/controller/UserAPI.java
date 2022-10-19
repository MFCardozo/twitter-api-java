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
        HttpGet getTestTw = new HttpGet(CommandUrl.BASE_URL + "/tweets/search/recent?query=from:twitterdev");
        String getMenuDataResponse = command.executeHttpMethod(getTestTw);
        // TODO: implementar api
        return getMenuDataResponse;
    }
}

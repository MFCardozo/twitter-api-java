package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.services.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hashtag")
public class HashtagAPI {
    private final HashtagService hashtagService;

    @Autowired
    public HashtagAPI(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }


}

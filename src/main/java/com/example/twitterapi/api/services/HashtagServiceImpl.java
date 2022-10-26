package com.example.twitterapi.api.services;

import com.example.twitterapi.api.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashtagServiceImpl implements HashtagService {

    private final Command command;

    @Autowired
    public HashtagServiceImpl(Command command) {
        this.command = command;
    }

}

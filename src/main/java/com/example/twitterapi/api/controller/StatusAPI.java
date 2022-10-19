package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ResponseConnectionStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusAPI {

    @Value("${apiVersion}")
    private String apiVersion;

    @GetMapping("")
    public ResponseConnectionStatus getConnectionStatus() {
        ResponseConnectionStatus r = new ResponseConnectionStatus();
        r.setBackendConnection(true);
        r.setBackendVersion(apiVersion);
        return r;
    }
}

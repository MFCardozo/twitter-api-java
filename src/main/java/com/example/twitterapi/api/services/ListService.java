package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface ListService {
    ResponseEntity<ApiResponse> saveByUsername(Map<String, String> userMap) throws IOException;

    ResponseEntity<ApiResponse> getAll();
}

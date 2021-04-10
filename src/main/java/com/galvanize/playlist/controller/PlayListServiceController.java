package com.galvanize.playlist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayListServiceController {

    @PostMapping("playlist")
    @ResponseStatus(HttpStatus.CREATED)
    public String postPlayList(@RequestBody String playlist) {
        return "Playlist created successfully!";
    }
}

package com.galvanize.playlist.controller;

import com.galvanize.playlist.dto.PlayListDto;
import com.galvanize.playlist.service.PlayListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class PlayListServiceController {

    private final PlayListService playListService;

    public PlayListServiceController(PlayListService playListService) {
        this.playListService = playListService;
    }

    @PostMapping("playlist")
    public ResponseEntity<?> postPlayList(@RequestBody PlayListDto playlist) {
        var result = this.playListService.savePlayList(playlist);
        var status = HttpStatus.CREATED;
        switch (result){
            case "Playlist created successfully!":
                status = HttpStatus.CREATED;
                break;
            case "Playlist is already exist":
                status = HttpStatus.CONFLICT;
                break;
            case "Playlist name should not be empty!":
                status = HttpStatus.PARTIAL_CONTENT;
                break;
        }
        var response = new ResponseEntity<>(Collections.singletonMap("message", result), status);
        return response;
    }


}

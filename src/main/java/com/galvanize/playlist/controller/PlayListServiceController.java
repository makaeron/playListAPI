package com.galvanize.playlist.controller;

import com.galvanize.playlist.dto.PlayListDto;
import com.galvanize.playlist.service.PlayListService;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String,String> postPlayList(@RequestBody PlayListDto playlist) {
        var result = this.playListService.savePlayList(playlist);
        return Collections.singletonMap("message", result);
    }
}

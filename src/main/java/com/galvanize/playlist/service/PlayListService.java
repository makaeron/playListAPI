package com.galvanize.playlist.service;

import com.galvanize.playlist.dto.PlayListDto;
import com.galvanize.playlist.entity.PlayListEntity;
import com.galvanize.playlist.repository.PlayListRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayListService {

    PlayListRepository playListRepository;

    public PlayListService(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    public String savePlayList(PlayListDto playListDto) {
        var result = this.playListRepository.save(
                new PlayListEntity(playListDto.getName(), playListDto.getSongs()));
        return result != null ? "Playlist created successfully!" : "Playlist creation failed!";
    }
}

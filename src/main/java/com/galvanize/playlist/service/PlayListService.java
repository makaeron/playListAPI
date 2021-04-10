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
        if (!playListDto.getName().isEmpty()) {
            var existingEntity = this.playListRepository.findAll().stream().filter(entity -> entity.getName().equalsIgnoreCase(playListDto.getName())).findAny().orElse(null);
            if (existingEntity == null) {
                var result = this.playListRepository.save(
                        new PlayListEntity(playListDto.getName(), playListDto.getSongs()));
                return result != null ? "Playlist created successfully!" : "Playlist creation failed!";
            } else {
                return "Playlist is already exist";
            }
        } else {
            return "Playlist name should not be empty!";
        }
    }

    public PlayListDto addSong(String playListName, String song) {
        var playListEntity = this.playListRepository.findAll().stream()
                .filter(entity -> entity.getName().equalsIgnoreCase(playListName))
                .findAny().orElse(null);
        if (playListEntity != null) {
            playListEntity.getSongs().add(song);
            this.playListRepository.save(playListEntity);
        }
        return new PlayListDto(playListEntity.getName(), playListEntity.getSongs());
    }

    public PlayListDto deleteSong(String playlistName, String songName) {
        var playListEntity = this.playListRepository.findAll().stream()
                .filter(entity -> entity.getName().equalsIgnoreCase(playlistName))
                .findAny().orElse(null);
        if (playListEntity != null) {
            playListEntity.getSongs().remove(songName);
            this.playListRepository.save(playListEntity);
        }
        return new PlayListDto(playListEntity.getName(), playListEntity.getSongs());
    }

    public PlayListDto getPlayList(String name) {
        var playListEntity = this.playListRepository.findAll().stream()
                .filter(entity -> entity.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
        return new PlayListDto(playListEntity.getName(), playListEntity.getSongs());
    }
}




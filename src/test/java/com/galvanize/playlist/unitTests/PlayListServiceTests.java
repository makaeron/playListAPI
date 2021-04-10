package com.galvanize.playlist.unitTests;

import com.galvanize.playlist.dto.PlayListDto;
import com.galvanize.playlist.entity.PlayListEntity;
import com.galvanize.playlist.repository.PlayListRepository;
import com.galvanize.playlist.service.PlayListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayListServiceTests {

    @Mock
    PlayListRepository playListRepository;

    @InjectMocks
    PlayListService playListService;

    @Test
    public void savePlayList() {
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("First Playlist", songs);
        var playListEntity = new PlayListEntity("First Playlist", songs);
        when(playListRepository.save(any())).thenReturn(playListEntity);

        var result = playListService.savePlayList(playListDto);

        verify(playListRepository).save(
                new PlayListEntity("First Playlist", songs)
        );
        assertThat(result).isEqualTo("Playlist created successfully!");
    }

    @Test
    public void duplicatePlayList() {
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("First Playlist", songs);
        var playListEntity = new PlayListEntity("First Playlist", songs);
        when(playListRepository.findAll()).thenReturn(List.of(playListEntity));
        var result = playListService.savePlayList(playListDto);
        verify(playListRepository, times(0)).save(
                new PlayListEntity("First Playlist", songs)
        );
        assertThat(result).isEqualTo("Playlist is already exist");
    }

    @Test
    public void  playListNameEmptyTest() {
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("", songs);

        var result = playListService.savePlayList(playListDto);

        verify(playListRepository, times(0)).findAll();
        verify(playListRepository, times(0)).save(
                new PlayListEntity("First Playlist", songs)
        );
        assertThat(result).isEqualTo("Playlist name should not be empty!");
    }

    @Test
    public void addSongToPlayListTest() {
        var playListName = "First Playlist";
        var songs = new ArrayList<String>();
        songs.add("First song");
        var playListEntity = new PlayListEntity(playListName, songs);
        when(playListRepository.findAll()).thenReturn(List.of(playListEntity));

        var secondSong = "Second Song";
        var playListDto = playListService.addSong(playListName, secondSong);

        songs.add(secondSong);
        verify(playListRepository).save(
                new PlayListEntity("First Playlist", songs)
        );
        assertThat(playListDto)
                .isEqualTo(new PlayListDto(playListEntity.getName(), playListEntity.getSongs()));
    }

}



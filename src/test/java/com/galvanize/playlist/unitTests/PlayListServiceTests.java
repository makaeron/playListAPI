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

        var result =  playListService.savePlayList(playListDto);

        verify(playListRepository).save(
                new PlayListEntity("First Playlist", songs)
        );
        assertThat(result).isEqualTo("Playlist created successfully!");
    }

}

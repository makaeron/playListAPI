package com.galvanize.playlist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.playlist.dto.PlayListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayListServiceIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createPlayList() throws Exception {
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("First Playlist", songs);

        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Playlist created successfully!"));
    }

}

package com.galvanize.playlist.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayListServiceIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createPlayList() throws Exception {
        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"First Playlist\",\"songs\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Playlist created successfully!"));
    }

}

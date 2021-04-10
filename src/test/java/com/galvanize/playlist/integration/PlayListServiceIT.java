package com.galvanize.playlist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.playlist.dto.PlayListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class PlayListServiceIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;
    //When a playlist is created with a name
    //Then a confirmation is returned that it was successful.
    //And the playlist is empty.

    @Test
    public void createPlayList() throws Exception {
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("First Playlist", songs);

        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Playlist created successfully!"))
                .andDo(document("Post-PlayList", responseFields(
                        fieldWithPath("message").description("Play list created or not message")
                )));
    }
    //When a playlist is created with existing name
    //Then a message is returned that it was unsuccessful.

    @Test
    public void duplicatePlayList() throws Exception{
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("First Playlist", songs);

        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Playlist created successfully!"));


        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value("Playlist is already exist"))
                .andDo(document("Post-PlayList", responseFields(
                        fieldWithPath("message").description("Play list created or not message"))));

    }

}

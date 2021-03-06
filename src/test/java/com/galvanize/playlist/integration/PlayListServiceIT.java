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

    /**
     * When a playlist is created with a name
     * Then a confirmation is returned that it was successful.
     * And the playlist is empty.
     */
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

    /**
     * When a playlist is created with existing name
     * Then a message is returned that it was unsuccessful.
     */
    @Test
    public void duplicatePlayList() throws Exception {
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
                        fieldWithPath("message").description("Play list already exist message"))));
    }

    /**
     * When a playlist is created without a name
     * Then a message is returned that a name is required.
     */
    @Test
    public void playListNameEmptyTest() throws Exception {
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("", songs);

        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isPartialContent())
                .andExpect(jsonPath("message").value("Playlist name should not be empty!"))
                .andDo(document("Post-PlayList", responseFields(
                        fieldWithPath("message").description("Play list name required message"))));
    }

    /**
     * Given a playlist
     * When a song is added
     * Then the playlist have one more song
     */
    @Test
    public void addSongToPlayListTest() throws Exception {
        var songs = new ArrayList<String>();
        var playListDto = new PlayListDto("First Playlist", songs);

        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Playlist created successfully!"));

        mockMvc.perform(put("/playlist/First Playlist/song")
                .contentType(MediaType.APPLICATION_JSON)
                .content("First Song"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("First Playlist"))
                .andExpect(jsonPath("songs[0]").value("First Song"))
                .andDo(document("Put-Song", responseFields(
                        fieldWithPath("name").description("Play list name"),
                        fieldWithPath("songs").description("Play list songs name")
                )));
    }
    /**
     * Given a playlist has songs
     * When a song is removed
     * Then the playlist have one less song
     */

    @Test
    public void removeSong() throws Exception {
        var songs = new ArrayList<String>();
        songs.add("Song Name");
        var playListDto = new PlayListDto("First Playlist", songs);
        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Playlist created successfully!"));
        mockMvc.perform(delete("/playlist/First PlayList/Song Name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("First Playlist"))
                .andExpect(jsonPath("songs.length()").value(0))
                .andDo(document("Delete-Song", responseFields(
                        fieldWithPath("name").description("Play list name"),
                        fieldWithPath("songs").description("Play list songs name")
                )));
    }

    /**
     * Given a playlist has songs
     * When retrieve the playlist
     * Then see the songs on the playlist
     */
    @Test
    public void  getPlayListSongsTest() throws Exception {
        var songs = new ArrayList<String>();
        songs.add("Song Name");
        var playListDto = new PlayListDto("First Playlist", songs);
        mockMvc.perform(post("/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(playListDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Playlist created successfully!"));

        mockMvc.perform(get("/playlist/First Playlist")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("First Playlist"))
                .andExpect(jsonPath("songs[0]").value("Song Name"))
                .andDo(document("Get-Song", responseFields(
                        fieldWithPath("name").description("Play list name"),
                        fieldWithPath("songs").description("Play list songs name")
                )));
    }
}

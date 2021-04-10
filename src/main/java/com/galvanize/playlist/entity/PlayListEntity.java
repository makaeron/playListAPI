package com.galvanize.playlist.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Data
public class PlayListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private ArrayList<String> songs;

    public PlayListEntity(String name, ArrayList<String> songs) {
        this.name = name;
        this.songs = songs;
    }
}

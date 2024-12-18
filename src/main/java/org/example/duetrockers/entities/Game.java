package org.example.duetrockers.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Games")
public class Game
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 100, nullable = false)
    private String gameName;

    @Column(name = "player_count")
    private Integer playerCount;


    public Game()
    {

    }

    public Game(String name, int playerCount)
    {
        this.gameName = name;
        this.playerCount = playerCount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

}


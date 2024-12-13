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

    @Column(name = "game_name", length = 100, nullable = false)
    private String gameName;

    @Column(name = "player_count")
    private int playerCount;

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

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}

package org.example.duetrockers.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "match_players")

public class MatchPlayer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "match_player_id")
    private int id;

    @ManyToOne
    @JoinColumn (name = "match_id", nullable = false )
    private Match match;

    @ManyToOne
    @JoinColumn (name = "player_id", nullable = false)
    private Player player;

    @Column(name = "score", nullable = false)
    private int score;


    //Getters och Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
package org.example.duetrockers.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "PlayerMatches")
public class MatchPlayer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player1_id", nullable = false)
    private Player player1;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player2_id", nullable = false)
    private Player player2;

    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;

    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;


    public MatchPlayer()
    {

    }

    public MatchPlayer(Game game, Player player1, Player player2, LocalDateTime matchDate, boolean completed)
    {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
        this.matchDate = matchDate;
        this.completed = completed;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

}
package org.example.duetrockers.entities;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table (name = "teams")
public class Team {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "name", unique = true, nullable = false)
    private String teamName;

    @OneToMany (mappedBy = "team",cascade = CascadeType.ALL)
    private Set<Player>players;

    @ManyToOne
    @JoinColumn (name= "game_id")
    private Game game;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName(){
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

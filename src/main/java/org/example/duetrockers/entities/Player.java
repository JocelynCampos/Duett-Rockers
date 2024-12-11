package org.example.duetrockers.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "players")
public class Player{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "player_id")
    private int id;

    @Column (name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column (name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column (name = "nickname", length = 100, unique = true, nullable = false)
    private String nickname;

    @ManyToOne
    @JoinColumn (name = "team_id")
    private Team team;

    //getter och setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
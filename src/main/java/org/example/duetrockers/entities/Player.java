package org.example.duetrockers.entities;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.EAGER;


@Entity
@Table (name = "players")
public class Player{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn (name = "team_id")
    private Team team;

    public Player()
    {

    }

    public Player(Person person, Team team)
    {
        this.person = person;
        this.team = team;
    }

    //getter och setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getNickname()
    {
        return person.getNickname();
    }

    public void setNickname(String newNickname)
    {
        person.setNickname(newNickname);
    }
}
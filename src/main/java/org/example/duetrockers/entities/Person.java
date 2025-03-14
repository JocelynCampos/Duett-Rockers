package org.example.duetrockers.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "Persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "street")
    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role")
    private Role role;

    public Person()
    {

    }

    public Person(String firstName, String lastName, String nickname, String street, String postalCode, String city, String country, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.email = email;
        this.role = role;
    }

    public Person(String firstName, String lastName, String nickname, String street, String postalCode, String city, String country, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

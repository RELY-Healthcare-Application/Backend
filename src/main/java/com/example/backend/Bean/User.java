package com.example.backend.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="UserName")
    private String userName;

    @Column(name="PhoneNo")
    private String phone_no;

    @Column(name="Email")
    private String email;

    @Column(name="Password")
    private String password;

    @OneToMany(targetEntity = Patient.class, mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //over
    private List<Patient> profiles;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(){
    }

    public User(String userName,String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public List<Patient> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Patient> profiles) {
        this.profiles = profiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
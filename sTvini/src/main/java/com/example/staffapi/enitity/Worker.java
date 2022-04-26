package com.example.staffapi.enitity;

import com.example.staffapi.enums.Day;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;


    // TODO saving to see generated passwords, should delete lates
    private String password;
    private String username;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "worker_project_mapping",
            joinColumns = { @JoinColumn(name = "worker_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "day")
    @MapKeyEnumerated
    private Map<Day, Project> projects;


    public Worker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Map<Day, Project> getProjects() {
        return projects;
    }

    public void setProjects(Map<Day, Project> projects) {
        this.projects = projects;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

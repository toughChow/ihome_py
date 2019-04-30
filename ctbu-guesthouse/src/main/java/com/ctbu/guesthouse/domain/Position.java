package com.ctbu.guesthouse.domain;

import javax.persistence.*;

@Entity
@Table(name = "gh_position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32 )
    private String positionName;

    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", insertable = false,updatable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public Position setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPositionName() {
        return positionName;
    }

    public Position setPositionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Position setUser(User user) {
        this.user = user;
        return this;
    }
}

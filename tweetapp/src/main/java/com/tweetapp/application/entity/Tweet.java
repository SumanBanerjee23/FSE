package com.tweetapp.application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    public Tweet(String description, User user) {
        this.description = description;
        this.user = user;
        this.createdOn = new Date();
        this.lastUpdatedOn = new Date();
    }

}

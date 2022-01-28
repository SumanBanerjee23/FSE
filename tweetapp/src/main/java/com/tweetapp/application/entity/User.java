package com.tweetapp.application.entity;

import com.tweetapp.application.constants.ErrorMessage;
import com.tweetapp.application.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true, nullable = false)
    @Email(message = "Invalid input. Please provide correct Email address")
    private String userName;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public User(@Email(message = "Invalid input. Please provide correct Email address") String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.createdOn = new Date();
        this.lastUpdatedOn = new Date();
    }
}

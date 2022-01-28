package com.tweetapp.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDetail {

    private String userName;
    private String password;
    @Nullable
    private Date createdOn;

    public UserDetail(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}

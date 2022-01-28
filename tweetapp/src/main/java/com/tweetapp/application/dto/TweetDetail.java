package com.tweetapp.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@NoArgsConstructor
public class TweetDetail {

    private String description;
    @Nullable
    private Date lastUpdatedOn;
    @Nullable
    private String userName;


    public TweetDetail(String description) {
        this.description = description;
    }

}

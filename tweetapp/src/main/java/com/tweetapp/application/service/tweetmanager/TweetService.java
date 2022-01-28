package com.tweetapp.application.service.tweetmanager;

import com.tweetapp.application.dto.TweetDetail;

import java.util.List;

public interface TweetService {

    /**
     * inserts tweet in storage
     * @param tweetDetail
     */
    void postTweet(TweetDetail tweetDetail);

    /**
     * gets all the tweets of the logged in user
     * @return
     */
    List<TweetDetail> getAllLoggedInUserTweets();

    /**
     * gets all the tweets of all users
     * @return
     */
    List<TweetDetail> getAllTweets();

}

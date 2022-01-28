package com.tweetapp.application.service.tweetmanager;

import com.tweetapp.application.dto.TweetDetail;
import com.tweetapp.application.entity.Tweet;
import com.tweetapp.application.entity.User;
import com.tweetapp.application.repositories.TweetRepository;
import com.tweetapp.application.service.usermanager.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TweetServiceImpl implements TweetService {

    @Autowired
    private UserService userService;

    @Autowired
    private TweetRepository tweetRepository;

    /**
     * inserts tweet in storage
     *
     * @param tweetDetail
     */
    @Override
    public void postTweet(TweetDetail tweetDetail) {
        User user = userService.getLoggedInUser();
        Tweet tweet = new Tweet(tweetDetail.getDescription(), user);
        tweetRepository.save(tweet);
    }

    /**
     * gets all the tweets of the logged in user
     *
     * @return
     */
    @Override
    public List<TweetDetail> getAllLoggedInUserTweets() {
        User user = userService.getLoggedInUser();
        return this.convertTweetToTweetDetails(tweetRepository.findAllByUser_Id(user.getId()));
    }

    /**
     * gets all the tweets of all users
     *
     * @return
     */
    @Override
    public List<TweetDetail> getAllTweets() {
        return this.convertTweetToTweetDetails(tweetRepository.findAll());
    }

    private List<TweetDetail> convertTweetToTweetDetails(Iterable<Tweet> tweets) {
        List<TweetDetail> tweetDetails = new ArrayList<TweetDetail>();
        tweets.forEach(tweet -> {
            TweetDetail tweetDetail = new TweetDetail();
            tweetDetail.setDescription(tweet.getDescription());
            tweetDetail.setLastUpdatedOn(tweet.getLastUpdatedOn());
            tweetDetail.setUserName(tweet.getUser().getUserName());
            tweetDetails.add(tweetDetail);
        });
        return tweetDetails;
    }
}

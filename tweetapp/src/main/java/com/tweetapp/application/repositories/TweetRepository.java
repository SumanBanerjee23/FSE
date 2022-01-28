package com.tweetapp.application.repositories;

import com.tweetapp.application.entity.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository  extends CrudRepository<Tweet,Long> {

    Iterable<Tweet> findAllByUser_Id(Long id);

}

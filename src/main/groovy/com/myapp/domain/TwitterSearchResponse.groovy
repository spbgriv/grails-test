package com.myapp.domain

import twitter4j.Status

class TwitterSearchResponse {

    final List<Status> tweets
    final Long nextMaxId

    TwitterSearchResponse(List<Status> tweets, Long nextMaxId) {
        this.tweets = tweets
        this.nextMaxId = nextMaxId
    }
}
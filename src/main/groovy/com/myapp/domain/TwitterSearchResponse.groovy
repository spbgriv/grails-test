package com.myapp.domain

import twitter4j.Status

class TwitterSearchResponse {

    final List<Status> tweets
    final Long nextMaxId
    final boolean hasNext;

    TwitterSearchResponse(List<Status> tweets, Long nextMaxId, boolean hasNext) {
        this.tweets = tweets
        this.nextMaxId = nextMaxId
        this.hasNext = hasNext
    }
}
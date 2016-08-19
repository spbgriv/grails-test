package com.myapp

import org.springframework.beans.factory.annotation.Value
import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

import javax.annotation.PostConstruct

class TwitterService {

    static lazyInit = false

    @Value('${twitter.consumer.key}')
    String twitterConsumerKey

    @Value('${twitter.consumer.secret}')
    String twitterConsumerSecret


    @Value('${twitter.access.token}')
    String twitterAccessToken

    @Value('${twitter.access.token.secret}')
    String twitterAccessTokenSecret

    private Twitter twitter

    @PostConstruct
    void init() {
        twitter = TwitterFactory.getSingleton()
        log.debug("access token: " + twitterAccessToken);
        AccessToken accessToken = new AccessToken(twitterAccessToken, twitterAccessTokenSecret);
        twitter.setOAuthConsumer(twitterConsumerKey, twitterConsumerSecret)
        twitter.setOAuthAccessToken(accessToken)
    }

    def search(String query) throws Exception {
        if (query == null || query.isEmpty()) throw new IllegalArgumentException("query can't be empty")
        QueryResult result = twitter.search(new Query(query))
        log.debug("query: " + query + ", count: " + result.count);
        return result
    }
}

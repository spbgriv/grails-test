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
        AccessToken accessToken = new AccessToken(twitterAccessToken, twitterAccessTokenSecret);
        twitter.setOAuthConsumer(twitterConsumerKey, twitterConsumerSecret)
        twitter.setOAuthAccessToken(accessToken)
        log.debug("initialized by access token")
    }

    def search(String searchQuery, Long maxId) throws Exception {
        if (!searchQuery?.trim()) throw new IllegalArgumentException("search query can't be empty")
        final Query query = new Query(searchQuery)
        if (maxId) query.setMaxId(maxId)
        final QueryResult result = twitter.search(query)
        log.debug("query: " + searchQuery + ", maxId: " + maxId + ", count: " + result.count);
        return result
    }
}

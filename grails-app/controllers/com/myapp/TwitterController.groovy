package com.myapp

import com.myapp.domain.TwitterSearchResponse
import grails.converters.JSON
import twitter4j.QueryResult

import javax.annotation.PostConstruct

class TwitterController {

    TwitterService twitterService

    @PostConstruct
    void init(){
        JSON.use('deep')
    }

    def search () {
        final String query = params.q
        if (!query?.trim()) {
            render contentType: "text/json", text: ''
            return;
        }
        final Long maxId = (params.maxId?.trim()) ? Long.parseLong((String)params.maxId) : null

        final QueryResult result = twitterService.search(query, maxId)

        render contentType: "text/json", text: new TwitterSearchResponse(result.getTweets(), result.nextQuery().maxId, result.hasNext()) as JSON
    }
}

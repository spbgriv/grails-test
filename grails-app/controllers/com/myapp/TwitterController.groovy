package com.myapp

import com.myapp.domain.TwitterSearchResponse
import grails.converters.JSON
import groovy.json.JsonBuilder
import twitter4j.QueryResult

class TwitterController {

    TwitterService twitterService

    def search () {
        JSON.use('deep')
        String query = params.q

        QueryResult result = twitterService.search(query)

        render contentType: "text/json", text: new TwitterSearchResponse(result.getTweets(), result.nextQuery().maxId) as JSON
    }
}

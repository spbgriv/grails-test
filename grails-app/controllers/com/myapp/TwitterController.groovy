package com.myapp

import com.myapp.domain.TwitterSearchResponse
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import twitter4j.QueryResult

class TwitterController {

    TwitterService twitterService

    @Secured(['ROLE_CONSUMER'])
    def search () {
        final String query = params.q
        if (!query?.trim()) {
            render contentType: "text/json", text: '{}'
        } else {
            final Long maxId = (params.maxId?.trim()) ? Long.parseLong((String)params.maxId) : null

            final QueryResult result = twitterService.search(query, maxId)

            render contentType: "text/json", text: new TwitterSearchResponse(result.getTweets(), (result.hasNext()) ? result.nextQuery().maxId : null, result.hasNext()) as JSON

        }
    }
}

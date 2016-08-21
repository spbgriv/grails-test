package com.myapp

import com.myapp.auth.User
import grails.converters.JSON

class RegisterController {

    static allowedMethods = [registerConsumer: 'POST']

    RegisterService registerService;

    def registerConsumer() {
        if (!params.username?.trim() || !params.password?.trim()) {
            render contentType: "text/json", text: [success: false, message: 'Password or login cannot be empty.'] as JSON;
        } else {
            def user = User.findByUsername(params.username)
            if (user) {
                render contentType: "text/json", text: [success: false, message: 'User already exists.'] as JSON
            } else {
                registerService.registerConsumer(params.username, params.password)
                log.debug("User: " + params.username + " successfully created.")
                render contentType: "text/json", text: [success: true] as JSON
            }
        }

    }
}

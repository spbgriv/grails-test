package com.myapp

import com.myapp.auth.Role
import com.myapp.auth.User
import com.myapp.auth.UserRole

import javax.annotation.PostConstruct

class RegisterService {

    private Role consumerRole;

    @PostConstruct
    void init(){
        consumerRole = new Role(authority: 'ROLE_CONSUMER').save(flush: true)
    }

    def registerConsumer(String login, String password) {
        final User consumer = new User(username: login, password: password).save(flush: true)
        return UserRole.create(consumer, consumerRole, true)
    }
}

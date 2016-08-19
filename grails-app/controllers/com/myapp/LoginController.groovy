package com.myapp

import com.myapp.auth.User

class LoginController {

    def login = {}

    def index() {
        redirect(action: "login")
    }

    def authenticate() {
        def user = User.findByLoginAndPassword(params.login, params.password)
        if(user){
            session.user = user
            flash.message = "Hello ${user.name}!"
            redirect(controller:"entry", action:"list")
        }else{
            flash.message = "Sorry, ${params.login}. Please try again."
            redirect(action:"login")
        }
    }
}

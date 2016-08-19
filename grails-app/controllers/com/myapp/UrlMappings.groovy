package com.myapp

class UrlMappings {

    static mappings = {
        "/login"(view: "/login")
        "/authenticate"(controller: 'login', action: 'authenticate')

        "/api/twitter/search?"(controller: 'twitter', action: 'search')
        "/public/twitter/search?"(controller: 'twitter', action: 'search')

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

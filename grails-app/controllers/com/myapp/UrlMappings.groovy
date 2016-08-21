package com.myapp

class UrlMappings {

    static mappings = {

        "/api/twitter/search?"(controller: 'twitter', action: 'search')
        "/register/consumer"(controller: 'register', action: 'registerConsumer')

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

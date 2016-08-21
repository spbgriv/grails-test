import com.myapp.RegisterService
import grails.converters.JSON

class BootStrap {

    RegisterService registerService;

    def init = { servletContext ->
        registerService.registerConsumer('user', 'pass')
        JSON.use('deep')
    }
    def destroy = {
    }
}

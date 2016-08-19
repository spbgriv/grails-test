import grails.util.BuildSettings
import grails.util.Environment

appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

logger 'grails.app.controllers.com.myapp', DEBUG, ['STDOUT'], false
logger 'grails.app.services.com.myapp', DEBUG, ['STDOUT'], false

root(ERROR, ['STDOUT'])
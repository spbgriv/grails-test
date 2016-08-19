import com.myapp.auth.Role
import com.myapp.auth.User
import com.myapp.auth.UserRole

class BootStrap {

    def init = { servletContext ->
            def admin = new Role(authority: 'ROLE_ADMIN').save(flush: true)
            def user = new User(username: 'user', password: 'pass').save(flush: true)

            UserRole.create user, admin, true
    }
    def destroy = {
    }
}

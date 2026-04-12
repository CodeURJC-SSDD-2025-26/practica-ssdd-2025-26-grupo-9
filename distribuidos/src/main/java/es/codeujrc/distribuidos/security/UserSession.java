package es.codeujrc.distribuidos.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import es.codeujrc.distribuidos.entity.User;

@Component
@SessionScope
public class UserSession {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogged() {
        return user != null;
    }
}
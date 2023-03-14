package ru.otus.spring.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Role;
import ru.otus.spring.domain.User;
import ru.otus.spring.repository.UserRepository;

@Component
public class Bootstrap implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("!!! create default users: admin, user1, user2 ");
        createUserWithRole("admin", "password", Role.ADMIN);
        createUserWithRole("user1", "1", Role.USER);
        createUserWithRole("user2", "2", Role.NO_RIGHT);
    }

    private void createUserWithRole(String username, String password, Role authority){
        if (userRepository.countByName(username) == 0){
            User user = new User(
                    null,
                    username,
                    new BCryptPasswordEncoder().encode(password),
                    authority
            );
            userRepository.save(user);
        }
    }
}

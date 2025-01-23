package isen.projet_dp_api.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Hardcoded user details for testing
        // TODO go search for real users in the database
        return switch (email) {
            case "test@mail.com" ->
                    new User("test@mail.com", "$2a$12$DRYIb0ui/UG4WpXv2dXtiezv/XGtgb7KFtKoSkzDnxSYYJa8r0ZXS", new ArrayList<>());
            case "john.doe@example.com" ->
                    new User("john.doe@example.com", "$2a$10$cpITvQEUABB86/gUZRnGB.JojZ44m7Ih.TtWEN5PuMdcHQieOull.", new ArrayList<>());
            case "labmanagerresearch@gmail.com" ->
                    new User("labmanagerresearch@gmail.com", "$2a$10$cpITvQEUABB86/gUZRnGB.JojZ44m7Ih.TtWEN5PuMdcHQieOull.", new ArrayList<>());
            case null, default -> throw new UsernameNotFoundException("User not found with email: " + email);
        };
    }
}
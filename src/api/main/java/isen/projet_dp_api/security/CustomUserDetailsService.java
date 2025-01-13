package isen.projet_dp_api.security;

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
        if ("test@mail.com".equals(email)) {
            return new User("test@mail.com", "$2a$12$DRYIb0ui/UG4WpXv2dXtiezv/XGtgb7KFtKoSkzDnxSYYJa8r0ZXS", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
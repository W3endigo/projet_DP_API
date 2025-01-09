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
        // Replace this with your actual user retrieval logic
        if ("test@mail.com".equals(email)) {
            return new User("user", "password", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with mail: " + email);
        }
    }
}
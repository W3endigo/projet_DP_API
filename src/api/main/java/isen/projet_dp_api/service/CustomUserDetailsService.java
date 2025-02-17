package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceDAO userServiceDAO;

    public CustomUserDetailsService(UserServiceDAO userServiceDAO){
        this.userServiceDAO = userServiceDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userServiceDAO.getUserByEmail(email);
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
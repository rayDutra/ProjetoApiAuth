//package com.nttdata.authentication.api.service;
//
//import com.nttdata.authentication.api.client.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private List<User> users = new ArrayList<>();
//
//    public UserDetailsServiceImpl() {
//        // Simulando alguns usuários em memória (substitua por lógica real, se necessário)
//        users.add(new User("user1@example.com", "password1"));
//        users.add(new User("user2@example.com", "password2"));
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User matchingUser = users.stream()
//            .filter(user -> user.getEmail().equals(username))
//            .findFirst()
//            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//        return org.springframework.security.core.userdetails.User.builder()
//            .username(matchingUser.getEmail())
//            .password(matchingUser.getSenha())
//            .roles("USER")  // Define as permissões do usuário, se necessário
//            .build();
//    }
//}

package com.Hayati.Reservation.des.Hotels.services;


import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.repositoriy.UserRepository;




@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrnumerodetelephone) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrNumerodetelephone(emailOrnumerodetelephone, emailOrnumerodetelephone)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email or numero de telephone: " + emailOrnumerodetelephone));

        Set<GrantedAuthority> authorities = new HashSet<>();
        // Ajoutez les autorisations de l'utilisateur ici, par exemple, en ajoutant un rôle fixe
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }
}

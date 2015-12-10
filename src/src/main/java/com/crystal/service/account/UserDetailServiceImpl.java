package com.crystal.service.account;

import com.crystal.model.entities.account.Role;
import com.crystal.repository.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.crystal.model.entities.account.User domainUser;

        try {
            domainUser = userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(domainUser == null)
            throw new UsernameNotFoundException("Usuario no encontrado");

        try{
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            List<GrantedAuthority> authorities = new ArrayList<>();
            Role role = domainUser.getRole();

            if(role != null){
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            return new User(
                    domainUser.getUsername(),
                    domainUser.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    authorities);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

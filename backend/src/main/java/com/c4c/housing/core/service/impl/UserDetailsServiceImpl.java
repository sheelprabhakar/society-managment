package com.c4c.housing.core.service.impl;

import com.c4c.housing.core.entity.RoleEntity;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.entity.UserRoleEntity;
import com.c4c.housing.core.repository.RoleRepository;
import com.c4c.housing.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = this.userRepository.findByEmail(username);

        if (user == null) {
            log.info("User Not found");
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        List<RoleEntity> roleEntities = new ArrayList<>();
        for(UserRoleEntity e: user.getRoles()){
            RoleEntity entity = this.roleRepository.findById(e.getRoleId()).get();
            roleEntities.add(entity);
        }
        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password("p")
                .authorities(roleEntities)//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}
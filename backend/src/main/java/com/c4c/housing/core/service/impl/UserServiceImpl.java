package com.c4c.housing.core.service.impl;

import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.UserRepository;
import com.c4c.housing.core.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(final UserEntity userEntity){
        return this.userRepository.save(userEntity);
    }

    @Override
    public UserEntity findById(final long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity update(final UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    @Override
    public void clearOTP(UserEntity userEntity) {
        this.userRepository.clearOTP(userEntity.getId());
    }

    @Override
    public boolean isOTPRequired(Long id) {
        UserEntity userEntity = this.userRepository.findById(id).orElse(null);
        if(userEntity == null){
            return false;
        }
        return userEntity.isOTPRequired();
    }

    public UserEntity delete(UserEntity userEntity) {
        return this.delete(userEntity);
    }
}

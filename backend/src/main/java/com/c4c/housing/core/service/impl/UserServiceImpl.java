package com.c4c.housing.core.service.impl;

import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.UserRepository;
import com.c4c.housing.core.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${society.management.otp.valid.duration:50000}")
    private long otpValidDuration = 500000;
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
    public UserEntity findById(final UUID id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity findByEmail(final String email) {
        return this.userRepository.findByEmail(email);
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
    public boolean isOTPRequired(UUID id) {
        UserEntity userEntity = this.userRepository.findById(id).orElse(null);
        if(userEntity == null){
            return false;
        }
        return this.isOTPRequired(userEntity);
    }

    @Override
    public boolean isOTPRequired(UserEntity userEntity) {
        if (userEntity.getOtp() == null) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = userEntity.getOtpAt().getTimeInMillis();

        if (otpRequestedTimeInMillis + this.otpValidDuration < currentTimeInMillis) {
            // OTP expires
            return false;
        }

        return true;
    }
    public UserEntity delete(UserEntity userEntity) {
        return this.delete(userEntity);
    }
}

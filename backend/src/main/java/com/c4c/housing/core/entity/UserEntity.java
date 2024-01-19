package com.c4c.housing.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    public static final double OTP_VALID_DURATION = 5;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "mobile", length = 15)
    private String mobile;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password_hash", length = 64, nullable = false)
    private String passwordHash;

    @Column(name = "otp", length = 64, nullable = false)
    private String otp;

    @Column(name = "otp_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar otpAt;

    @Column(name = "registered_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registeredAt;

    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastLogin;

    @Column(name = "intro")
    private String intro;

    @Column(name = "profile")
    private String profile;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;
    public boolean isOTPRequired() {
        if (this.getOtp() == null) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpAt.getTimeInMillis();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expires
            return false;
        }

        return true;
    }

}

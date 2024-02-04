package com.c4c.housing.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends CommonEntityAttributes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

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

    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastLogin;

    @Column(name = "intro")
    private String intro;

    @Column(name = "profile")
    private String profile;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private List<UserRoleEntity> roles;

}

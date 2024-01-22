package com.c4c.housing.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "tenant")
@Getter
@Setter
@NoArgsConstructor
public class TenantEntity extends CommonEntityAttributes{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "address", length = 512, nullable = true)
    private String address;

    @Column(name = "pin", length = 8, nullable = true)
    private String pin;

    @Column(name = "phone", length = 45, nullable = true)
    private String phone;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive;
}

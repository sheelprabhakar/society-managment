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

import java.io.Serializable;
import java.util.UUID;

/**
 * The type Tenant entity.
 */
@Entity(name = "tenant")
@Getter
@Setter
@NoArgsConstructor
public class TenantEntity extends CommonEntityAttributes implements Serializable {
    /**
     * The constant L255.
     */
    private static final int L255 = 255;
    /**
     * The constant L512.
     */
    private static final int L512 = 512;
    /**
     * The constant L15.
     */
    private static final int L15 = 45;
    /**
     * The constant L8.
     */
    private static final int L8 = 8;
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    /**
     * The Name.
     */
    @Column(name = "name", length = L255, nullable = false)
    private String name;

    /**
     * The Email.
     */
    @Column(name = "email", length = L255, nullable = false)
    private String email;

    /**
     * The Address.
     */
    @Column(name = "address", length = L512, nullable = true)
    private String address;

    /**
     * The Pin.
     */
    @Column(name = "pin", length = L8, nullable = true)
    private String pin;

    /**
     * The Phone.
     */
    @Column(name = "phone", length = L15, nullable = true)
    private String phone;

    /**
     * The Is active.
     */
    @Column(name = "is_active", nullable = true)
    private Boolean isActive;
}

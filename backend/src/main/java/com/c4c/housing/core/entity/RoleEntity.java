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
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type Role entity.
 */
@Entity(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity extends CommonEntityAttributes implements GrantedAuthority, Serializable {

    /**
     * The constant L50.
     */
    private static final int L50 = 50;
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
    @Column(name = "name", length = L50)
    private String name;

    /**
     * Gets authority.
     *
     * @return the authority
     */
    @Override
    public String getAuthority() {
        return name;
    }
}

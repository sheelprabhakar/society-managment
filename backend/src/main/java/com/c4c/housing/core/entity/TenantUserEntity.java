package com.c4c.housing.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

/**
 * The type Tenant user entity.
 */
@Getter
@Setter
@Entity(name = "tenant_user")
@NoArgsConstructor
@IdClass(TenantUserEntityId.class)
public class TenantUserEntity extends CommonEntityAttributes {
    /**
     * The Role id.
     */
    @Id
    @Column(name = "tenant_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID tenantId;

    /**
     * The User id.
     */
    @Id
    @Column(name = "user_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;

}

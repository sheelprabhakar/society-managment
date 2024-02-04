package com.c4c.housing.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@IdClass(UserRoleId.class)
public class UserRoleEntity extends CommonEntityAttributes implements Serializable {

	@Id
	@Column(name = "role_id", nullable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID roleId;

	@Id
	@Column(name = "user_id", nullable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID userId;
}
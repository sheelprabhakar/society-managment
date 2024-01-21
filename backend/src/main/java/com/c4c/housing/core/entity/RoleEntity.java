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

import java.util.UUID;

@Entity(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity extends CommonEntityAttributes implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID id;

	@Column(name = "name", length = 50)
	private String name;

	@Override
	public String getAuthority() {
		return name;
	}
}
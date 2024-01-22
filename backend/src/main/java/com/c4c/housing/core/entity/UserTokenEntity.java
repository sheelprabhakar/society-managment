package com.c4c.housing.core.entity;

import com.c4c.housing.core.service.impl.EntityAttributeEncryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Calendar;
import java.util.UUID;

@Entity(name = "user_token")
@Getter
@Setter
@NoArgsConstructor
public class UserTokenEntity {

	@Id
	@Column(name = "id", nullable = false)
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID id;

	@Column(name = "token", nullable = false, length = 4096)
	@Convert(converter = EntityAttributeEncryptor.class)
	private String token;

	@Column(name = "updated_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedAt;
}
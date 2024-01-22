package com.c4c.housing.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
public class CommonEntityAttributes {

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

    @Column(name = "created_by", length = 255, nullable = true)
    private String createdBy;

    @Column(name = "updated_by", length = 255, nullable = true)
    private String updatedBy;
}

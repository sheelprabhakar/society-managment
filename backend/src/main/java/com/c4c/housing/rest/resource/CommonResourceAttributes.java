package com.c4c.housing.rest.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
public class CommonResourceAttributes {
    private boolean isDeleted;
    private Calendar createdAt;
    private Calendar updatedAt;
    private String createdBy;
    private String updatedBy;
}

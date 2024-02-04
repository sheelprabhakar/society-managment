package com.c4c.housing.rest.resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

/**
 * The type Common resource attributes.
 */
@Getter
@Setter
@NoArgsConstructor
public class CommonResourceAttributes {
    /**
     * The Is deleted.
     */
    private boolean isDeleted;
    /**
     * The Created at.
     */
    private Calendar createdAt;
    /**
     * The Updated at.
     */
    private Calendar updatedAt;
    /**
     * The Created by.
     */
    private String createdBy;
    /**
     * The Updated by.
     */
    private String updatedBy;
}

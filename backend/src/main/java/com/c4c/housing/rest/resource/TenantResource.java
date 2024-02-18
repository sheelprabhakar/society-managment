package com.c4c.housing.rest.resource;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Resource for {@link com.c4c.housing.core.entity.TenantEntity}.
 */
@Data
@NoArgsConstructor
public class TenantResource extends CommonResourceAttributes implements Serializable {
    /**
     * The Id.
     */
    private UUID id;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Email.
     */
    private String email;
    /**
     * The Address.
     */
    private String address;
    /**
     * The Pin.
     */
    private String pin;
    /**
     * The Phone.
     */
    private String phone;
    /**
     * The Is active.
     */
    private Boolean isActive;
    /**
     * The Short name.
     */
    @NotNull
    @Size(max = 45)
    private String shortName;
    /**
     * The Area.
     */
    @Size(max = 255)
    private String area;
    /**
     * The Landmark.
     */
    @Size(max = 45)
    private String landmark;
    /**
     * The Picture url.
     */
    @Size(max = 2048)
    private String pictureUrl;
    /**
     * The Latitude.
     */
    private BigDecimal latitude;
    /**
     * The Longitude.
     */
    private BigDecimal longitude;
    /**
     * The Active.
     */
    @NotNull
    private boolean active;

    /**
     * The Mobile.
     */
    @NotNull
    private String mobile;
}

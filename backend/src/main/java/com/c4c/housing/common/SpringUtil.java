package com.c4c.housing.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The type Spring util.
 */
public final class SpringUtil {
    /**
     * Instantiates a new Spring util.
     */
    private SpringUtil() {

    }

    /**
     * Is tenant admin boolean.
     *
     * @return the boolean
     */
    public static boolean isTenantAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(Constants.TENANT_ADMIN))) {
            return true;
        }
        return false;
    }

    /**
     * Gets tenant id.
     *
     * @return the tenant id
     */
    public static String getTenantId() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            return request.getHeader("X-TENANT-ID");
        } else {
            return null;
        }
    }
}

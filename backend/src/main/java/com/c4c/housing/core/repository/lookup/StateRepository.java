package com.c4c.housing.core.repository.lookup;

import com.c4c.housing.core.entity.lookup.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface State repository.
 */
public interface StateRepository extends JpaRepository<StateEntity, Integer> {
    /**
     * Find by country id list.
     *
     * @param countryId the country id
     * @return the list
     */
    List<StateEntity> findByCountryId(int countryId);
}

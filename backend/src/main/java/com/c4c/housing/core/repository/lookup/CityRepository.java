package com.c4c.housing.core.repository.lookup;

import com.c4c.housing.core.entity.lookup.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface City repository.
 */
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
    /**
     * Find by state id list.
     *
     * @param stateId the state id
     * @return the list
     */
    List<CityEntity> findByStateId(int stateId);
}

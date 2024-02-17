package com.c4c.housing.core.repository.lookup;

import com.c4c.housing.core.entity.lookup.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface City repository.
 */
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
}

package com.c4c.housing.core.repository.lookup;

import com.c4c.housing.core.entity.lookup.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface State repository.
 */
public interface StateRepository extends JpaRepository<StateEntity, Integer> {
}

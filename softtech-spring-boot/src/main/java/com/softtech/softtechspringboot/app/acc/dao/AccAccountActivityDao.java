package com.softtech.softtechspringboot.app.acc.dao;

import com.softtech.softtechspringboot.app.acc.entity.AccAccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Repository
public interface AccAccountActivityDao extends JpaRepository<AccAccountActivity, Long> {
}

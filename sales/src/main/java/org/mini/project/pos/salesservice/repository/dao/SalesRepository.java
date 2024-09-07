package org.mini.project.pos.salesservice.repository.dao;

import org.mini.project.pos.salesservice.repository.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    Sales findById(long id);
}
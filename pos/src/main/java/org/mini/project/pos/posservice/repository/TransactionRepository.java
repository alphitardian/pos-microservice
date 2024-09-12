package org.mini.project.pos.posservice.repository;

import org.mini.project.pos.posservice.model.transaction.TransactionReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionReport, Long> {
}
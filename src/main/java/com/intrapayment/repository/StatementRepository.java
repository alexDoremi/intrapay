package com.intrapayment.repository;

import com.intrapayment.repository.entity.StatementEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends
    JpaRepository<com.intrapayment.repository.entity.StatementEntity, Long> {

  //get all statements for given id, would be creditId or debitId and limit by 20
  @Query("select s from StatementEntity s where s.creditId = :id or s.debitId = :id order by s.date desc limit 20 offset 0")
  public List<StatementEntity> findByCreditIdOrDebitId(Long id);
}

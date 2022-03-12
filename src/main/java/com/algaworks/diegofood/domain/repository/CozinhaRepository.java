package com.algaworks.diegofood.domain.repository;

import com.algaworks.diegofood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha,Long> {


}

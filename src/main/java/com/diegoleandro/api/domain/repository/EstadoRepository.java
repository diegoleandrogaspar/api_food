package com.diegoleandro.api.domain.repository;

import com.diegoleandro.api.domain.model.Cozinha;
import com.diegoleandro.api.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}

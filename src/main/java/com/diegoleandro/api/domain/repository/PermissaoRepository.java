package com.diegoleandro.api.domain.repository;


import com.diegoleandro.api.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}

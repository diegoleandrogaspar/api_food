package com.algaworks.diegofood.infrastructure.repository;

import com.algaworks.diegofood.domain.model.Restaurante;
import com.algaworks.diegofood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome,
              BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = builder.createQuery(Restaurante.class);
        criteriaQuery.from(Restaurante.class);

        TypedQuery<Restaurante> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();

    }
}

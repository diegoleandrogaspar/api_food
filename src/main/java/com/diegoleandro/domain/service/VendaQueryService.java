package com.diegoleandro.domain.service;

import com.diegoleandro.domain.filter.VendaDiariaFilter;
import com.diegoleandro.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}

package net.mouta.algafood.domain.service;

import net.mouta.algafood.domain.filter.VendaDiariaFilter;
import net.mouta.algafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

}

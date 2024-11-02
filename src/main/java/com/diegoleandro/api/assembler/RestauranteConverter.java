package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.RestauranteDTO;
import com.diegoleandro.api.model.input.RestauranteInput;
import com.diegoleandro.domain.model.Cidade;
import com.diegoleandro.domain.model.Cozinha;
import com.diegoleandro.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteConverter implements Converter<Restaurante, RestauranteDTO, RestauranteInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
       return modelMapper.map(restauranteInput, Restaurante.class);
    }

    @Override
    public RestauranteDTO toDto(Restaurante restaurante) {
      return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    @Override
    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toDto(restaurante))
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.diegoleandro.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }

    }




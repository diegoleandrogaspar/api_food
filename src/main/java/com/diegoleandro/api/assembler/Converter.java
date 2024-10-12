package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.input.RestauranteInput;
import com.diegoleandro.domain.model.Restaurante;

import java.util.List;

public interface Converter<T, S, U> {

    public T toDomainObject(U input);

    public S toDto(T domain);

    public List<S> toCollectionDTO(List<T> list);

    public void copyToDomainObject(U input, T type);

}

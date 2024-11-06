package com.diegoleandro.api.assembler;

import com.diegoleandro.api.model.input.RestauranteInput;
import com.diegoleandro.domain.model.Restaurante;

import java.util.Collection;
import java.util.List;

public interface Converter<T, S, U> {

    public T toDomainObject(U input);

    public S toDto(T domain);

    public Collection<S> toCollectionDTO(Collection<T> list);

    public void copyToDomainObject(U input, T type);

}

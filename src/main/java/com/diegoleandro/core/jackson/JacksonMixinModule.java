package com.diegoleandro.core.jackson;

import com.diegoleandro.api.model.mixin.CozinhaMixin;
import com.diegoleandro.domain.model.Cidade;
import com.diegoleandro.domain.model.Cozinha;
import com.diegoleandro.domain.model.Restaurante;
import com.diegoleandro.api.model.mixin.CidadeMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }

}

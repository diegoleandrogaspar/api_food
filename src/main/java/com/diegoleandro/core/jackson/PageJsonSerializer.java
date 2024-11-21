package com.diegoleandro.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator json, SerializerProvider serializer) throws IOException {

        json.writeStartObject();

        json.writeObjectField("content", page.getContent());
        json.writeNumberField("size", page.getSize());
        json.writeNumberField("totalElements", page.getTotalElements());
        json.writeNumberField("totalPages", page.getTotalPages());
        json.writeNumberField("number", page.getNumber());

        json.writeEndObject();

    }
}

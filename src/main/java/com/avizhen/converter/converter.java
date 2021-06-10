package com.avizhen.converter;

public interface Converter<E, D> {

    E convertToEntity(D dto);

    E convertEntityToUpdate(D dto, E entity);
}
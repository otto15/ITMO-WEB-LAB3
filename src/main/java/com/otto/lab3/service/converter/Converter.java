package com.otto.lab3.service.converter;

public interface Converter<F, T> {
    F map(T entity);

    T unmap(F dto);
}

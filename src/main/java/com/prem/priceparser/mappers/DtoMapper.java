package com.prem.priceparser.mappers;

import java.util.List;
import java.util.stream.Collectors;

public interface DtoMapper<O, D> {

    D convertToDto(O object);

    O convertFromDto(D dto);

    default List<D> convertToDtos(List<O> objects) {
        return objects.parallelStream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    default List<O> convertFromDtos(List<D> dtos) {
        return dtos.parallelStream()
                .map(this::convertFromDto)
                .collect(Collectors.toList());
    }
}

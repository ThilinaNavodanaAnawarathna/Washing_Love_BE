package com.sliit.washing_love_be.service;

import com.sliit.washing_love_be.dto.SparePartDto;

import java.util.List;

public interface SparePartService {
    SparePartDto save(SparePartDto sparePartDto) throws Exception;

    SparePartDto update(SparePartDto sparePartDto) throws Exception;

    boolean delete(Long sparePartId) throws Exception;

    SparePartDto findById(Long sparePartId) throws Exception;

    List<SparePartDto> getAllSpareParts() throws Exception;
}

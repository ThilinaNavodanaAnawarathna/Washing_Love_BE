package com.sliit.washing_love_be.service.impl;

import com.sliit.washing_love_be.dto.SparePartDto;
import com.sliit.washing_love_be.entity.SparePart;
import com.sliit.washing_love_be.enumz.InStock;
import com.sliit.washing_love_be.repository.SparePartRepository;
import com.sliit.washing_love_be.service.SparePartService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SparePartServiceImpl implements SparePartService {
    private final ModelMapper modelMapper;
    private final SparePartRepository sparePartRepository;

    public SparePartServiceImpl(ModelMapper modelMapper, SparePartRepository sparePartRepository) {
        this.modelMapper = modelMapper;
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public SparePartDto save(SparePartDto sparePartDto) throws Exception {
        try {
            SparePart sparePart = modelMapper.map(sparePartDto, SparePart.class);
            sparePart.setBrand(sparePartDto.getBrand());
            sparePart.setInStock(InStock.YES);
            SparePart save = sparePartRepository.save(sparePart);
            return modelMapper.map(save, SparePartDto.class);
        } catch (Exception e) {
            log.error("SparePartService : Can't save Spare Part | Error : {}", e.getMessage());
            throw new Exception("Can't save Spare Part details");
        }
    }

    @Override
    public SparePartDto update(SparePartDto sparePartDto) throws Exception {
        try {
            Optional<SparePart> byId = sparePartRepository.findById(sparePartDto.getId());
            if (!byId.isPresent())
                throw new Exception("Can't find Spare Part details");
            SparePart update = sparePartRepository.save(SparePart.builder().id(sparePartDto.getId())
                    .model(sparePartDto.getModel())
                    .brand(sparePartDto.getBrand())
                    .year(sparePartDto.getYear())
                    .partName(sparePartDto.getPartName())
                    .inStock(sparePartDto.getInStock())
                    .price(sparePartDto.getPrice()).build());
            return modelMapper.map(update, SparePartDto.class);

        } catch (Exception e) {
            log.error("SparePartService : Can't update Spare Part | Error : {}", e.getMessage());
            throw new Exception("Can't update Spare Part details");
        }
    }

    @Override
    public boolean delete(Long sparePartId) throws Exception {
        try {
            Optional<SparePart> byId = sparePartRepository.findById(sparePartId);
            if (!byId.isPresent())
                throw new Exception("Can't find Spare Part details");
            sparePartRepository.delete(byId.get());
            return true;
        } catch (Exception e) {
            log.error("SparePartService : Can't delete pare Part | Error : {}", e.getMessage());
            throw new Exception("Can't delete pare Part details");
        }
    }

    @Override
    public SparePartDto findById(Long sparePartId) throws Exception {
        try {
            Optional<SparePart> byId = sparePartRepository.findById(sparePartId);
            if (!byId.isPresent())
                throw new Exception("Can't find Spare Part details");
            return modelMapper.map(byId.get(), SparePartDto.class);
        } catch (Exception e) {
            log.error("SparePartService : Can't find Spare Part | Error : {}", e.getMessage());
            throw new Exception("Can't find Spare Part details");
        }
    }

    @Override
    public List<SparePartDto> getAllSpareParts() throws Exception {
        try {
            return sparePartRepository.findAll().stream()
                    .map(element -> modelMapper.map(element, SparePartDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("SparePartService : Can't get All Spare Part | Error : {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}

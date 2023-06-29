package com.sliit.washing_love_be.controller;

import com.sliit.washing_love_be.dto.SparePartDto;
import com.sliit.washing_love_be.service.SparePartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sparePart")
public class SparePartController {
    private final SparePartService sparePartService;

    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    @PostMapping
    public ResponseEntity<?> addSparePart(@RequestBody SparePartDto sparePartDto) throws Exception {
        SparePartDto save = sparePartService.save(sparePartDto);
        if (save == null)
            return new ResponseEntity<>("Can't add Spare Part", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> updateSparePart(@RequestBody SparePartDto sparePartDto) throws Exception {
        SparePartDto update = sparePartService.update(sparePartDto);
        if (update == null)
            return new ResponseEntity<>("Can't update Spare Part", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSparePart(@RequestParam Long sparePartId) throws Exception {
        boolean delete = sparePartService.delete(sparePartId);
        if (!delete)
            return new ResponseEntity<>("Can't delete Spare Part", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }

    @GetMapping("sparePart")
    public ResponseEntity<?> findById(@RequestParam Long sparePartId) throws Exception {
        SparePartDto sparePartDto = sparePartService.findById(sparePartId);
        if (sparePartDto == null)
            return new ResponseEntity<>("Can't find Spare Part", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(sparePartDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getSpareParts() {
        try {
            List<SparePartDto> allSpareParts = sparePartService.getAllSpareParts();
            return new ResponseEntity<>(allSpareParts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

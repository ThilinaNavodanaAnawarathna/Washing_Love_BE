package com.sliit.washing_love_be.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Value("${upload.path}")
    private String uploadDirectory;

    @GetMapping(value = "/download_document/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) throws Exception {

        File img = new File(uploadDirectory + "/" + id);

        String extension = "pdf";

        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(img.getName());

        if (extension.equalsIgnoreCase("pdf")) {
            mimeType = "application/pdf";
        }

        String fileName = id;

        return ResponseEntity.ok().contentType(MediaType.valueOf(mimeType)).header("Content-disposition", "attachment; filename=" + fileName).body(Files.readAllBytes(img.toPath()));
    }
}

package de.mtech.ai.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@org.springframework.web.bind.annotation.RestController
public class DownloadController {

    @GetMapping("/api/filedownload/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) throws IOException {

        File f = new File(Finals.UPLOAD_DIRECTORY + filename);
        System.out.println(f.getAbsoluteFile().toPath() + " - " + Files.probeContentType(f.toPath()));
        InputStreamResource inStreamResource = new InputStreamResource(Files.newInputStream(f.toPath()));

        String mediaTypeString = Files.probeContentType(f.toPath());
        MediaType mediaType = mediaTypeString != null ? MediaType.valueOf(Files.probeContentType(f.toPath())) : null;

        HttpHeaders headers = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename(filename)
                .build();
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(f.length())
                .contentType(mediaType != null ? mediaType : MediaType.APPLICATION_OCTET_STREAM)
                .body(inStreamResource);
    }
}

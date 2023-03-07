package de.mtech.ai.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@org.springframework.web.bind.annotation.RestController
public class UploadController {

    @PostMapping("/api/fileupload")
    public void uploadImage(@RequestParam("multipartFile") MultipartFile uploadfile) throws IOException {
        if (uploadfile.isEmpty()) {
            System.out.println("please select a file!");
        }

        if(!Files.exists(Paths.get(Finals.UPLOAD_DIRECTORY))){
            Files.createDirectories(Paths.get(Finals.UPLOAD_DIRECTORY));
        }

        Path path = Paths.get(Finals.UPLOAD_DIRECTORY + uploadfile.getOriginalFilename());
        Files.deleteIfExists(path);

        InputStream inStream = uploadfile.getInputStream();
        FileOutputStream outStream = new FileOutputStream(path.toFile());
        IOUtils.copyLarge(inStream, outStream);
        System.out.println("written " + path);
    }
}

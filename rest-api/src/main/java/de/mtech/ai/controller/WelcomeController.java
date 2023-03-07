package de.mtech.ai.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String getFilesupload(){
        return "filesupload";
    }

    @GetMapping("/all")
    public String getAllFiles(Model model){
        List<String> files = FileUtils.listFiles(new File(Finals.UPLOAD_DIRECTORY), null, true).stream().map(File::getName).collect(Collectors.toList());
        model.addAttribute("files", files);
        return "filesdownload";
    }
}

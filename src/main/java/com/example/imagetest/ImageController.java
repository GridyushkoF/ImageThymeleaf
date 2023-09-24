package com.example.imagetest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
public class ImageController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image, Model model) {
        if (image.isEmpty()) {
            model.addAttribute("message", "Please select an image to upload");
            return "upload";
        }

        try {
            System.out.println(image.getOriginalFilename());
            File file = new File(uploadPath, Objects.requireNonNull(image.getOriginalFilename()));
            image.transferTo(file);
            model.addAttribute("message", "Image uploaded successfully");
            model.addAttribute("imageName", image.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload image");
        }

        return "upload";
    }
}
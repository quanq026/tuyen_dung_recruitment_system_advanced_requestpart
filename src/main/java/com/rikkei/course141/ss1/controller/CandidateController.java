package com.rikkei.course141.ss1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

import com.rikkei.course141.ss1.dto.CandidateDTO;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final Path uploadDir = Paths.get("uploads");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/apply")
    public ResponseEntity<?> apply(
            @RequestPart("candidate") String candidateJson,
            @RequestPart("cv") MultipartFile cv) throws Exception {
        CandidateDTO dto = objectMapper.readValue(candidateJson, CandidateDTO.class);
        if (cv == null || cv.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "CV là bắt buộc"));
        }
        String original = cv.getOriginalFilename();
        if (original == null || !original.toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body(Map.of("error", "CV phải là PDF"));
        }
        Files.createDirectories(uploadDir);
        String newName = UUID.randomUUID() + "_" + original;
        Files.copy(cv.getInputStream(), uploadDir.resolve(newName), StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok(Map.of("message", "Ứng tuyển thành công", "cvFile", newName, "candidate", dto));
    }
}

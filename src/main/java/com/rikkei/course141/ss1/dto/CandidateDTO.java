package com.rikkei.course141.ss1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class CandidateDTO {
    @NotBlank
    private String fullName;
    @NotBlank
    @Email
    private String email;
    private List<String> skills;
}

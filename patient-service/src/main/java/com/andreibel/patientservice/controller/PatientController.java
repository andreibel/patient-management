package com.andreibel.patientservice.controller;


import com.andreibel.patientservice.DTO.PatientRequestDto;
import com.andreibel.patientservice.DTO.PatientResponseDto;
import com.andreibel.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;


    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        return ResponseEntity.ok().body(patientService.getPatients());
    }


    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto newPatient) {
        PatientResponseDto patientResponseDto = patientService.createPatient(newPatient);
        return ResponseEntity.ok().body(patientResponseDto);
    }


}

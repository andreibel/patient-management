package com.andreibel.patientservice.DTO;

import com.andreibel.patientservice.model.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Patient}
 */
public record PatientResponseDto(@NotNull String id, @NotNull String name, @NotNull @Email String email,
                                 @NotNull String address,
                                 @NotNull String dateOfBirth) implements Serializable {
}
package com.sequence.generator.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record CreateSequenceRequest(
        @NotBlank(message = "Sequence name is required")
        @Size(max = 50, message = "Sequence name must not exceed 50 characters")
        @Pattern(regexp = "^[A-Z_]+$", message = "Sequence name must be uppercase letters and underscores only")
        @JsonProperty("sequenceName")  String sequenceName,

        @Min(value = 0, message = "Initial value must be >= 0")
        @JsonProperty("initialValue")  Long initialValue,

        @Min(value = 1, message = "Increment must be at least 1")
        @Max(value = 1000, message = "Increment must not exceed 1000")
        @JsonProperty("incrementBy")   Integer incrementBy
) {}
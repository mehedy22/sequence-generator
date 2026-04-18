package com.sequence.generator.controller;

import com.sequence.generator.model.dto.request.CreateSequenceRequest;
import com.sequence.generator.model.dto.response.SequenceResponseDTO;
import com.sequence.generator.service.SequenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sequences")
@Slf4j
@Validated
@Tag(name = "Sequence Generator", description = "APIs for generating unique sequences")
@AllArgsConstructor
public class SequenceController {

    private final SequenceService sequenceService;

    @PostMapping("/{sequenceName}/next")
    @Operation(summary = "Get next value", description = "Returns the next unique value for a sequence")
    public ResponseEntity<SequenceResponseDTO> getNextSequence(
            @PathVariable String sequenceName) {
        log.info("Request for next sequence: {}", sequenceName);
        return ResponseEntity.ok(sequenceService.getNextSequence(sequenceName));
    }

    @GetMapping("/{sequenceName}/current")
    @Operation(summary = "Get current value", description = "Returns current value without incrementing")
    public ResponseEntity<SequenceResponseDTO> getCurrentSequenceValue(
            @PathVariable String sequenceName) {
        log.info("Request for current value: {}", sequenceName);
        return ResponseEntity.ok(sequenceService.getCurrentSequenceValue(sequenceName));
    }

    @PutMapping("/{sequenceName}/reset")
    @Operation(summary = "Reset sequence", description = "Resets sequence to a specific value. Use with caution.")
    public ResponseEntity<Void> resetSequence(
            @PathVariable String sequenceName,
            @RequestParam
            @Min(value = 0, message = "Reset value must be >= 0")
            long value) {
        log.warn("Reset requested for sequence '{}' to value {}", sequenceName, value);
        sequenceService.resetSequence(sequenceName, value);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Create sequence", description = "Registers a new named sequence")
    public ResponseEntity<SequenceResponseDTO> createSequence(
            @RequestBody @Valid CreateSequenceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sequenceService.createSequence(request));
    }
}
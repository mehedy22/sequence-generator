package com.sequence.generator.model.dto.response;

public record SequenceResponseDTO(
        String sequenceName,
        String value,
        long timestamp
) {
}
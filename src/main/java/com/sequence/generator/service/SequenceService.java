package com.sequence.generator.service;

import com.sequence.generator.model.dto.request.CreateSequenceRequest;
import com.sequence.generator.model.dto.response.SequenceResponseDTO;

public interface SequenceService {
    SequenceResponseDTO getNextSequence(String sequenceName);

    SequenceResponseDTO getCurrentSequenceValue(String sequenceName);

    void resetSequence(String sequenceName, long startValue);

    SequenceResponseDTO createSequence(CreateSequenceRequest request);
}
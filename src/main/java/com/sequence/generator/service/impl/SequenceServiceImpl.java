package com.sequence.generator.service.impl;


import com.sequence.generator.entity.Sequence;
import com.sequence.generator.exception.SequenceNotFoundException;
import com.sequence.generator.mapper.SequenceMapper;
import com.sequence.generator.model.dto.request.CreateSequenceRequest;
import com.sequence.generator.model.dto.response.SequenceResponseDTO;
import com.sequence.generator.repository.SequenceRepository;
import com.sequence.generator.service.SequenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
@AllArgsConstructor
public class SequenceServiceImpl implements SequenceService {

    private static final int MAX_BATCH_SIZE = 1000;
    private final SequenceRepository sequenceRepository;
    private final SequenceMapper sequenceMapper;

    @Override
    @Transactional
    public SequenceResponseDTO getNextSequence(String sequenceName) {
        log.info("Fetching next sequence for: {}", sequenceName);

        Sequence sequence = sequenceRepository.findBySequenceNameWithLock(sequenceName)
                .orElseThrow(() -> new SequenceNotFoundException("Sequence not found: " + sequenceName));

        long nextValue = sequence.getCurrentValue() + sequence.getIncrementBy();
        sequence.setCurrentValue(nextValue);
        sequenceRepository.save(sequence);

        log.info("Generated sequence {} for: {}", nextValue, sequenceName);
        return sequenceMapper.toResponseDto(sequence, Instant.now().toEpochMilli());
    }

    @Override
    @Transactional(readOnly = true)
    public SequenceResponseDTO getCurrentSequenceValue(String sequenceName) {
        log.info("Fetching current value for: {}", sequenceName);

        Sequence sequence = sequenceRepository.findBySequenceName(sequenceName)
                .orElseThrow(() -> new SequenceNotFoundException("Sequence not found: " + sequenceName));

        return sequenceMapper.toResponseDto(sequence, Instant.now().toEpochMilli());
    }


    @Override
    @Transactional
    public void resetSequence(String sequenceName, long startValue) {
        if (startValue < 0) {
            throw new IllegalArgumentException("Start value must be >= 0");
        }
        log.warn("Resetting sequence '{}' to value {}", sequenceName, startValue);

        Sequence sequence = sequenceRepository.findBySequenceNameWithLock(sequenceName)
                .orElseThrow(() -> new SequenceNotFoundException("Sequence not found: " + sequenceName));

        sequence.setCurrentValue(startValue);
        sequenceRepository.save(sequence);

        log.warn("Sequence '{}' successfully reset to {}", sequenceName, startValue);
    }

    @Override
    @Transactional
    public SequenceResponseDTO createSequence(CreateSequenceRequest request) {
        log.info("Creating sequence: {} with initial: {}", request.sequenceName(), request.initialValue());

        if (sequenceRepository.existsBySequenceName(request.sequenceName())) {
            throw new IllegalArgumentException("Sequence already exists: " + request.sequenceName());
        }

        Sequence sequence = sequenceMapper.toEntity(request.sequenceName(), request.initialValue(), request.incrementBy());
        Sequence saved = sequenceRepository.save(sequence);

        return sequenceMapper.toResponseDto(saved, Instant.now().toEpochMilli());
    }
}
package com.sequence.generator.mapper;

import com.sequence.generator.entity.Sequence;
import com.sequence.generator.model.dto.response.SequenceResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class SequenceMapper {

    public SequenceResponseDTO toResponseDto(Sequence sequence, long timestampMillis) {
        if (sequence == null) return null;

        String formattedValue = sequence.getSequenceName()
                + "-"
                + sequence.getCurrentValue();

        return new SequenceResponseDTO(
                sequence.getSequenceName(),
                formattedValue,
                timestampMillis
        );
    }

    public Sequence toEntity(String sequenceName, Long initialValue, Integer incrementBy) {
        Sequence sequence = new Sequence();
        sequence.setSequenceName(sequenceName);
        sequence.setCurrentValue(initialValue != null ? initialValue : 0L);
        sequence.setIncrementBy(incrementBy != null ? incrementBy : 1);
        return sequence;
    }
}

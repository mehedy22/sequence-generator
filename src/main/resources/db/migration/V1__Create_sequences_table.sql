CREATE TABLE IF NOT EXISTS sequences (
                                         id            BIGSERIAL PRIMARY KEY,
                                         sequence_name VARCHAR(50)  NOT NULL UNIQUE,
    current_value BIGINT       NOT NULL DEFAULT 0,
    increment_by  INTEGER      NOT NULL DEFAULT 1,
    version       BIGINT       NOT NULL DEFAULT 0,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_increment_by  CHECK (increment_by > 0),
    CONSTRAINT chk_current_value CHECK (current_value >= 0)
    );

CREATE INDEX IF NOT EXISTS idx_sequence_name ON sequences(sequence_name);
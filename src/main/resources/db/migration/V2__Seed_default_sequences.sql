INSERT INTO sequences (sequence_name, current_value, increment_by)
VALUES
    ('ORDER',   1000, 1),
    ('USER',       0, 1),
    ('INVOICE',  500, 1)
ON CONFLICT (sequence_name) DO NOTHING;
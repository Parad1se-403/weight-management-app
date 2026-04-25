CREATE TABLE daily_log (
    log_date DATE PRIMARY KEY,
    weight_kg NUMERIC(5,2) NOT NULL,
    sleep_hours NUMERIC(4,1),
    snack_type VARCHAR(100),
    drink_1 VARCHAR(100),
    drink_2 VARCHAR(100),
    drink_3 VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE app_setting (
    setting_id INTEGER PRIMARY KEY,
    height_cm NUMERIC(5,2),
    target_weight_kg NUMERIC(5,2),
    target_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
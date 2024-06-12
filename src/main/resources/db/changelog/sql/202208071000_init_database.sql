-- liquibase formatted sql
-- 2022-08-07 10:00:00 VN
-- changeset hoanglinh.duong:202208071000_init_database
DO $$
  BEGIN
    CREATE TABLE IF NOT EXISTS user_inform (
      id BIGSERIAL PRIMARY KEY,
      username TEXT UNIQUE NOT NULL,
      password TEXT,
      first_name TEXT,
      last_name TEXT,
      day_of_birth DATE,
      address JSONB,
      phone_number TEXT,
      email TEXT,
      role_code TEXT
    );

    CREATE TABLE IF NOT EXISTS role (
      code TEXT PRIMARY KEY,
      name JSONB
    );

    CREATE TABLE IF NOT EXISTS authority (
      code TEXT PRIMARY KEY,
      name JSONB,
      options JSONB
    );

    CREATE TABLE IF NOT EXISTS role_authority (
      role_code TEXT,
      authority_code TEXT,
      authority_div TEXT,

      PRIMARY KEY (role_code, authority_code)
    );

    ALTER TABLE user_inform ADD FOREIGN KEY (role_code) REFERENCES role (code);

    ALTER TABLE role_authority ADD FOREIGN KEY (role_code) REFERENCES role (code);

    ALTER TABLE role_authority ADD FOREIGN KEY (authority_code) REFERENCES authority (code);

    INSERT INTO authority (code, name, options)
    VALUES ('ACCOUNT_REGISTER', '{"en": "Account register"}',
		   '{"ENABLE": {"en": "ENABLE"}, "DISABLE": {"en": "DISABLE"}, "REFERENCE": {"en":"REFERENCE"}}')
    ON CONFLICT DO NOTHING;

    INSERT INTO role (code, name)
    VALUES ('USER', '{"en": "User role"}')
         , ('ADMIN', '{"en": "Admin role"}')
    ON CONFLICT DO NOTHING;

    INSERT INTO role_authority (role_code, authority_code, authority_div)
    VALUES ('USER', 'ACCOUNT_REGISTER', 'ENABLE')
         , ('ADMIN', 'ACCOUNT_REGISTER', 'ENABLE')
    ON CONFLICT DO NOTHING;
  END;
$$;
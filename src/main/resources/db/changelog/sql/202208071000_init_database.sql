-- liquibase formatted sql
-- 2022-08-07 10:00:00 VN
-- changeset hoanglinh.duong:202208071000_init_database
DO $$
  BEGIN
    CREATE TABLE IF NOT EXISTS users (
      id BIGSERIAL PRIMARY KEY,
      first_name varchar[50],
      last_name varchar[50],
      day_of_birth date,
      address json,
      phone_number varchar[15],
      email varchar[50],
      role_code varchar[10]
    );

    CREATE TABLE IF NOT EXISTS role (
      code varchar[10] PRIMARY KEY,
      name jsonb
    );

    CREATE TABLE IF NOT EXISTS authority (
      code varchar[10] PRIMARY KEY,
      name jsonb,
      options
    );

    CREATE TABLE IF NOT EXISTS role_authority (
      role_code varchar[10],
      authority_code varchar[10],
      authority_div varchar[50]
      PRIMARY KEY (role_code, authority_code)
    );

    ALTER TABLE users ADD FOREIGN KEY (role_code) REFERENCES role (code);

    ALTER TABLE role_authority ADD FOREIGN KEY (role_code) REFERENCES role (code);

    ALTER TABLE role_authority ADD FOREIGN KEY (authority_code) REFERENCES authority (code);

    INSERT INTO authority (code, name,
                        options)
    VALUES ("ACCOUNT_REGISTER", "{\"en\":\"Account register\"}",
            "{\"DISABLE\":{\"en\":\"DISABLE\"},\"REGISTER\":{\"en\":\"REGISTER\"},\"REFERENCE\":{\"en\":\"REFERENCE\"}}") ON CONFLICT DO UPDATE;

    INSERT INTO role (code, name)
    VALUES ("USER", "{\"en\":\"User role\"}")
         , ("ADMIN", "{\"en\":\"Admin role\"}") ON CONFLICT DO UPDATE;

    INSERT INTO role_authority (role_code, authority_code, authority_div)
    VALUES ("USER", "ACCOUNT_REGISTER", "")
         , ("ADMIN", "ACCOUNT_REGISTER") ON CONFLICT DO UPDATE;
  END;
$$;
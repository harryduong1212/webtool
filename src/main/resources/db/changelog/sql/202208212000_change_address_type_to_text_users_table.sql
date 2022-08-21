-- liquibase formatted sql
-- 2022-08-21 20:00:00 VN
-- changeset hoanglinh.duong:202208212000_change_address_type_to_text_users_table
DO $$
  BEGIN
    ALTER TABLE users ALTER COLUMN address TYPE TEXT;
  END;
$$;

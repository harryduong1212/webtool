-- liquibase formatted sql
-- 2022-08-20 14:45:00 VN
-- changeset hoanglinh.duong:202208201445_add_username_and_password_to_users_table
DO $$
  BEGIN
    ALTER TABLE IF EXISTS users
        ADD COLUMN IF NOT EXISTS username TEXT NOT NULL;
    ALTER TABLE IF EXISTS users
        ADD COLUMN IF NOT EXISTS password TEXT NOT NULL;
  END;
$$;

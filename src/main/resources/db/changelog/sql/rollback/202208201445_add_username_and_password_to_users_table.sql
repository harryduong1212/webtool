-- liquibase formatted sql
-- 2022-08-20 14:45:00 VN
-- changeset hoanglinh.duong:202208201445_add_username_and_password_to_users_table
DO $$
  BEGIN
    ALTER TABLE IF EXISTS users
        DROP COLUMN IF EXISTS username CASCADE;
    ALTER TABLE IF EXISTS users
        DROP COLUMN IF EXISTS password CASCADE;
  END;
$$;

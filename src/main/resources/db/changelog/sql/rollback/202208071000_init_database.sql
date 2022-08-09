-- liquibase formatted sql
-- 2022-08-07 10:00:00 VN
-- changeset hoanglinh.duong:202208071000_init_database

DO $$
  BEGIN
    DROP TABLE IF EXISTS role_authority CASCADE;
    DROP TABLE IF EXISTS role CASCADE;
    DROP TABLE IF EXISTS authority CASCADE;
    DROP TABLE IF EXISTS users CASCADE;
  END;
$$;
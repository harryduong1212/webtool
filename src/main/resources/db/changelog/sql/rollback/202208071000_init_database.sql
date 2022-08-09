-- liquibase formatted sql
-- 2022-08-07 10:00:00 VN
-- changeset hoanglinh.duong:202208071000_init_database
DO $$
  BEGIN
    DROP TABLE IF EXISTS user;
    DROP TABLE IF EXISTS role;
    DROP TABLE IF EXISTS authority;
    DROP TABLE IF EXISTS role_authority;
  END;
$$;
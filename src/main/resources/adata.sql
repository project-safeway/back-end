INSERT INTO usuarios (id, ativo, created_at, updated_at, nome, email, password_hash, role, tel1, tel2)
VALUES (random_bytes(16), true, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'Admin', 'admin',
        '$2a$10$8lwarDgK2sqiKAJKIR8vD.a0XtUYURmaUT5oHx4xwAUQn2NT09qNm', 'ADMIN', '00000000000', NULL);
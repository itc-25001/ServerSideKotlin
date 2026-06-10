USE book_manager;

INSERT INTO book
VALUES (100,'Kotlin入門','コトリン太郎','1950-10-01'),
       (200,'Java入門','ジュヴァ太郎','2005-08-29');
INSERT INTO user
VALUES (1,'admin@test.com','$argon2id$v=19$m=19456,t=2,p=1$SXUwQ1pVVm9ReHVPb05KUA$YMMad45C+MOmr+7vG1ySrkcCVI7FZg9rCe4NNDjgcHc
','管理者','ADMIN'),
       (2,'user@test.com','$argon2id$v=19$m=19456,t=2,p=1$bzcvUnIvd1N5aGtDd3ZBNg$tVgP60TSrQfIPQOUuHEk3vWOPWFA7oq0iwzpVfV45Zs
','ユーザー','USER');
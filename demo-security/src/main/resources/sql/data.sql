INSERT INTO perfis VALUES (1,'ADMIN'),(2,'MEDICO'),(3,'PACIENTE');

INSERT INTO perfis VALUES (1,'ADMIN')
 Select '1448523' Where not exists(select * from tablename where code='1448523')
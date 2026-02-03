SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE usuarios_roles;
TRUNCATE TABLE role;
TRUNCATE TABLE usuarios;
TRUNCATE TABLE barbeiro_especialidades;
TRUNCATE TABLE horario_trabalho;
TRUNCATE TABLE servico;
TRUNCATE TABLE barbeiro;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. Definição de Roles
INSERT INTO role (id, name) VALUES (1, 'ADMIN'), (2, 'BARBER'), (3, 'CLIENT');

-- 2. Criação dos Usuários (Contas de Acesso)
INSERT INTO usuarios (id, full_name, email, password) VALUES
(1, 'Ariel o Brabo', 'ariel@barbearia.com', '123456'),
(2, 'Lucas do Corte', 'lucas@barbearia.com', '123456'),
(3, 'Juliana Silver', 'juliana@barbearia.com', '123456'),
(4, 'Marcos Navalha', 'marcos@barbearia.com', '123456'),
(5, 'Ricardo Inativo', 'ricardo@barbearia.com', '123456');

-- 3. Atribuição de Permissões (Todos recebem ID 2 - BARBER)
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES 
(1, 2), (2, 2), (3, 2), (4, 2), (5, 2);

-- 4. Serviços Disponíveis
INSERT INTO servico (id, nome, descricao, preco, duracao, especialidade) VALUES
(1, 'Corte Masculino', 'Corte tesoura e máquina com acabamento', 40.00, 40, 'CORTE_DE_CABELO'),
(2, 'Barba Premium', 'Barba com toalha quente e óleo', 35.00, 30, 'BARBA'),
(3, 'Sobrancelha', 'Design de sobrancelha na navalha', 20.00, 15, 'SOBRANCELHA'),
(4, 'Pigmentação', 'Disfarce de falhas na barba/cabelo', 45.00, 30, 'PIGMENTACAO'),
(5, 'Progressiva', 'Alinhamento térmico dos fios', 90.00, 90, 'QUIMICA');

-- 5. Barbeiros (Vínculo da Entidade com o Usuário)
INSERT INTO barbeiro (id, nome, foto_path, esta_ativo, usuario_id) VALUES
(1, 'Ariel o Brabo', 'ariel.jpg', 1, 1),
(2, 'Lucas do Corte', 'lucas.jpg', 1, 2),
(3, 'Juliana Silver', 'juliana.png', 1, 3),
(4, 'Marcos Navalha', 'marcos.png', 1, 4),
(5, 'Ricardo Inativo', 'ricardo.jpg', 0, 5);

-- 6. Especialidades dos Barbeiros
INSERT INTO barbeiro_especialidades (barbeiro_id, especialidades) VALUES
(1, 'CORTE_DE_CABELO'), (1, 'BARBA'), (1, 'PIGMENTACAO'),
(2, 'CORTE_DE_CABELO'), (2, 'SOBRANCELHA'),
(3, 'QUIMICA'), (3, 'CORTE_DE_CABELO'),
(4, 'BARBA'),
(5, 'CORTE_DE_CABELO');

-- 7. Agenda de Trabalho
INSERT INTO horario_trabalho (dia_da_semana, hora_inicio, hora_fim, barbeiro_id) VALUES
('MONDAY', '08:00:00', '18:00:00', 1),
('TUESDAY', '08:00:00', '18:00:00', 1),
('WEDNESDAY', '10:00:00', '20:00:00', 2),
('THURSDAY', '10:00:00', '20:00:00', 2),
('FRIDAY', '10:00:00', '22:00:00', 2);
-- Desabilita constraints para limpar tudo sem erros de FK
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE barbeiro_especialidades;
TRUNCATE TABLE horario_trabalho;
TRUNCATE TABLE servico;
TRUNCATE TABLE barbeiro;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. SERVIÇOS (Baseado na sua entidade Servico)
INSERT INTO servico (id, nome, descricao, preco, duracao, especialidade) VALUES
(1, 'Corte Masculino', 'Corte tesoura e máquina com acabamento', 40.00, 40, 'CORTE_DE_CABELO'),
(2, 'Barba Premium', 'Barba com toalha quente e óleo', 35.00, 30, 'BARBA'),
(3, 'Sobrancelha', 'Design de sobrancelha na navalha', 20.00, 15, 'SOBRANCELHA'),
(4, 'Pigmentação', 'Disfarce de falhas na barba/cabelo', 45.00, 30, 'PIGMENTACAO'),
(5, 'Progressiva', 'Alinhamento térmico dos fios', 90.00, 90, 'QUIMICA');

-- 2. BARBEIROS (5 Barbeiros para teste)
INSERT INTO barbeiro (id, nome, foto_path, esta_ativo) VALUES
(1, 'Ariel o Brabo', 'barbeiros/ariel.jpg', 1),
(2, 'Lucas do Corte', 'barbeiros/lucas.jpg', 1),
(3, 'Juliana Silver', 'barbeiros/juliana.jpg', 1),
(4, 'Marcos Navalha', 'barbeiros/marcos.jpg', 1),
(5, 'Ricardo Inativo', 'barbeiros/ricardo.jpg', 0);

-- 3. ESPECIALIDADES (Relacionamento Many-to-Many/ElementCollection)
INSERT INTO barbeiro_especialidades (barbeiro_id, especialidades) VALUES
(1, 'CORTE_DE_CABELO'), (1, 'BARBA'), (1, 'PIGMENTACAO'), -- Especialista em tudo
(2, 'CORTE_DE_CABELO'), (2, 'SOBRANCELHA'),
(3, 'QUIMICA'), (3, 'CORTE_DE_CABELO'),
(4, 'BARBA'),
(5, 'CORTE_DE_CABELO');

-- 4. HORÁRIOS DE TRABALHO (Exemplos para testes de agenda)
INSERT INTO horario_trabalho (dia_da_semana, hora_inicio, hora_fim, barbeiro_id) VALUES
-- Ariel trabalha Segunda e Terça
('MONDAY', '08:00:00', '18:00:00', 1),
('TUESDAY', '08:00:00', '18:00:00', 1),
-- Lucas trabalha de Quarta a Sexta
('WEDNESDAY', '10:00:00', '20:00:00', 2),
('THURSDAY', '10:00:00', '20:00:00', 2),
('FRIDAY', '10:00:00', '22:00:00', 2);
CREATE TABLE barbeiro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    foto_path VARCHAR(255),
    esta_ativo BOOLEAN DEFAULT TRUE
);

-- Tabela auxiliar para o ElementCollection de Especialidades
CREATE TABLE barbeiro_especialidades (
    barbeiro_id BIGINT NOT NULL,
    especialidades VARCHAR(50) NOT NULL,
    CONSTRAINT fk_barbeiro_especialidade FOREIGN KEY (barbeiro_id) REFERENCES barbeiro(id)
);
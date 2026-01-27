CREATE TABLE horario_trabalho (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dia_da_semana VARCHAR(20) NOT NULL, -- MONDAY, TUESDAY...
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    barbeiro_id BIGINT NOT NULL,
    CONSTRAINT fk_horario_barbeiro FOREIGN KEY (barbeiro_id) REFERENCES barbeiro(id)
);
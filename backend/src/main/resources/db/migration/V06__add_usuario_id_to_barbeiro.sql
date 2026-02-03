ALTER TABLE barbeiro ADD COLUMN usuario_id BIGINT UNIQUE;
ALTER TABLE barbeiro ADD CONSTRAINT fk_barbeiro_usuario 
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);
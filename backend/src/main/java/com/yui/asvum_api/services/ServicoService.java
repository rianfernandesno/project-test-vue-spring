package com.yui.asvum_api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yui.asvum_api.models.dtos.ServicoOutput;
import com.yui.asvum_api.models.dtos.input.ServicoInput;
import com.yui.asvum_api.models.entities.Servico;
import com.yui.asvum_api.models.mapper.ServicoMapper;
import com.yui.asvum_api.repository.ServicoRepository;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;

    @Autowired
    private ServicoMapper mapper;

    @Transactional(readOnly = true)
    public List<ServicoOutput> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toOutput)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServicoOutput findById(Long id) {
        return repository.findById(id)
                .map(mapper::toOutput)
                .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado com ID: " + id));
    }

    @Transactional
    public ServicoOutput create(ServicoInput input) {
        Servico servico = mapper.toEntity(input);
        servico = repository.save(servico);
        return mapper.toOutput(servico);
    }

    @Transactional
    public ServicoOutput update(Long id, ServicoInput input) {
        Servico servico = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado com ID: " + id));

        mapper.updateEntityFromDto(input, servico);
        servico = repository.save(servico);

        return mapper.toOutput(servico);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Serviço não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}

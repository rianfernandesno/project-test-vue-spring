package com.yui.asvum_api.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yui.asvum_api.models.dtos.BarbeiroOutput;
import com.yui.asvum_api.models.dtos.input.BarbeiroInput;
import com.yui.asvum_api.models.entities.Barbeiro;
import com.yui.asvum_api.models.mapper.BarbeiroMapper;
import com.yui.asvum_api.repository.BarbeiroRepository;

@Service
public class BarbeiroService {

     @Value("${app.upload.dir}")
    private String baseUrl;

     @Autowired
    private BarbeiroRepository repository;

    @Autowired
    private BarbeiroMapper mapper;

    @Transactional(readOnly = true)
    public List<BarbeiroOutput> findAll(){

        return repository.findAll()
                .stream()
                .map(mapper::toOutput)
                .toList();
    }

    @Transactional(readOnly = true)
    public BarbeiroOutput findById(Long id){
        return repository.findById(id)
                .map(mapper::toOutput)
                .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado"));
    }

    @Transactional(readOnly = true)
    public BarbeiroOutput findByName(String name){
        return repository.findByNome(name)
                .map(mapper::toOutput)
                .orElseThrow(() -> new NoSuchElementException("Serviço não encontrado"));
    }

    @Transactional
    public BarbeiroOutput create(BarbeiroInput input) {
        Barbeiro barbeiro = mapper.toEntity(input);

        barbeiro = repository.save(barbeiro);

        createDirectorie(barbeiro.getId());

        return mapper.toOutput(barbeiro);
    }

    @Transactional
    public BarbeiroOutput update(Long id, BarbeiroInput input) {
        Barbeiro barbeiroExistente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Barbeiro não encontrado"));

        mapper.updateEntityFromDto(input, barbeiroExistente);

        return mapper.toOutput(repository.save(barbeiroExistente));
    }

    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }

    private void createDirectorie(Long id){
        Path path = Paths.get(baseUrl).resolve("barbeiros").resolve(String.valueOf(id)).resolve("imagens");
        
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

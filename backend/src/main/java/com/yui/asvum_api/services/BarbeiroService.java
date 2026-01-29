package com.yui.asvum_api.services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public BarbeiroOutput create(BarbeiroInput input, MultipartFile file) {
        Barbeiro barbeiro = mapper.toEntity(input);
        barbeiro = repository.save(barbeiro);

        if (file != null && !file.isEmpty()) {
            saveImage(file, barbeiro);
        }

        return mapper.toOutput(barbeiro);
    }

    @Transactional
    public BarbeiroOutput update(Long id, BarbeiroInput input, MultipartFile file) {
        Barbeiro barbeiroExistente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Barbeiro não encontrado"));

        barbeiroExistente.getHorariosTrabalho().clear();

        mapper.updateEntityFromDto(input, barbeiroExistente);

        if (file != null && !file.isEmpty()) {
            deleteOldImage(barbeiroExistente);
            saveImage(file, barbeiroExistente); 
        }

        return mapper.toOutput(repository.save(barbeiroExistente));
    }

    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }

     private void saveImage(MultipartFile file, Barbeiro barbeiro) {
        createDirectorie(barbeiro.getId());

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetPath = getPath(barbeiro.getId()).resolve(fileName);

        try {
            file.transferTo(targetPath);
            barbeiro.setFotoPath(fileName);
            repository.save(barbeiro);
        } catch (IOException e) {
            throw new RuntimeException("Erro crítico ao salvar o arquivo físico", e);
        }
    }

    private void deleteOldImage(Barbeiro barbeiro) {
        if (barbeiro.getFotoPath() != null) {
            Path pathAntigo = getPath(barbeiro.getId()).resolve(barbeiro.getFotoPath());
            try {
                Files.deleteIfExists(pathAntigo);
            } catch (IOException e) {
                System.err.println("Aviso: Não foi possível deletar arquivo antigo: " + e.getMessage());
            }
        }
    }

    private void createDirectorie(Long id){
        Path path = getPath(id);
        
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getPath(Long id){
        return Paths.get(baseUrl)
        .resolve("barbeiros")
        .resolve(id.toString())
        .resolve("imagens")
        .toAbsolutePath()
        .normalize();
    }   

}

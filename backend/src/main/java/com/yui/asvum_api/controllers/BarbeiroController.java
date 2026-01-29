package com.yui.asvum_api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yui.asvum_api.models.dtos.BarbeiroOutput;
import com.yui.asvum_api.models.dtos.input.BarbeiroInput;
import com.yui.asvum_api.services.BarbeiroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {

    @Autowired
    private BarbeiroService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<BarbeiroOutput>> findAll(){
        List<BarbeiroOutput> listBarbeiro = service.findAll();

        return ResponseEntity.ok().body(listBarbeiro);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<BarbeiroOutput> create( @RequestPart("input") @Valid BarbeiroInput input, @RequestPart("file") MultipartFile file){

        BarbeiroOutput output = service.create(input, file);

        URI uri  = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(output.id()).toUri();
        return ResponseEntity.created(uri).body(output);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<BarbeiroOutput> update(@RequestPart("id") Long id, @RequestPart("input") @Valid BarbeiroInput input, @RequestPart("file") MultipartFile file){
       BarbeiroOutput output = service.update(id, input, file); 


       return ResponseEntity.ok().body(output);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}

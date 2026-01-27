package com.yui.asvum_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yui.asvum_api.models.dtos.ServicoOutput;
import com.yui.asvum_api.services.ServicoService;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService service;

    @GetMapping
    private ResponseEntity<List<ServicoOutput>> findAll(){
        
        List<ServicoOutput> listService = service.findAll();

        return ResponseEntity.ok().body(listService);
    }
}

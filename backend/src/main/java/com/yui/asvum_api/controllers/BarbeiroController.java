package com.yui.asvum_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yui.asvum_api.models.dtos.BarbeiroOutput;
import com.yui.asvum_api.services.BarbeiroService;

@RestController
@RequestMapping("/barbeiro")
public class BarbeiroController {

    @Autowired
    private BarbeiroService service;

    @GetMapping
    private ResponseEntity<List<BarbeiroOutput>> findAll(){
        List<BarbeiroOutput> listBarbeiro = service.findAll();

        return ResponseEntity.ok().body(listBarbeiro);
    }
}

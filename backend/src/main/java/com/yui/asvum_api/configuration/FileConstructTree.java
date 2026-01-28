package com.yui.asvum_api.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yui.asvum_api.models.dtos.BarbeiroOutput;
import com.yui.asvum_api.services.BarbeiroService;

@Component
public class FileConstructTree  implements CommandLineRunner{
    
    @Value("${app.upload.dir}")
    private String baseUrl;

    @Override
    public void run(String... args) throws Exception {
        try{
            Path path = Paths.get(baseUrl);
            Files.createDirectories(path);
            Files.createDirectories(path.resolve("profiles"));
            Files.createDirectories(path.resolve("barbeiros"));

        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
        
    }
}

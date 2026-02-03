package com.yui.asvum_api.models.dtos;

import java.util.List;

public record UsuarioOutput(
     Long id,
     String fullName,
     String email,
     List<String> roles
){}

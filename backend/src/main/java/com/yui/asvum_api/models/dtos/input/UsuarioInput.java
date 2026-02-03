package com.yui.asvum_api.models.dtos.input;

import java.util.List;

public record UsuarioInput(
    
    String fullName,
    String email,
    String password,
    List<Long> rolesIds
    
){

}
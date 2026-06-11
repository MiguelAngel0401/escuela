package com.miguel.escuela.controllers;

import com.miguel.escuela.dto.grupos.GruposRequest;
import com.miguel.escuela.dto.grupos.GruposResponse;
import com.miguel.escuela.services.grupo.GrupoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GruposRequest, GruposResponse, GrupoService>{
    public GrupoController(GrupoService service) {
        super(service);
    }
}

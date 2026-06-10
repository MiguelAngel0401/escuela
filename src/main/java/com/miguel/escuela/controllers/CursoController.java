package com.miguel.escuela.controllers;

import com.miguel.escuela.dto.aulas.AulaResponse;
import com.miguel.escuela.dto.cursos.CursoRequest;
import com.miguel.escuela.dto.cursos.CursoResponse;
import com.miguel.escuela.services.aulas.AulaService;
import com.miguel.escuela.services.curso.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends  CommonController<CursoRequest, CursoResponse, CursoService> {

    public CursoController(CursoService service) {
        super(service);
    }
}

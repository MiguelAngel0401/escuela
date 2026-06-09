package com.miguel.escuela.controllers;

import com.miguel.escuela.dto.aulas.AulaRequest;
import com.miguel.escuela.dto.aulas.AulaResponse;
import com.miguel.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {
    public AulaController(AulaService service) {
        super(service);
    }
}

package com.miguel.escuela.controllers;

import com.miguel.escuela.dto.horarios.HorarioRequest;
import com.miguel.escuela.dto.horarios.HorarioResponse;
import com.miguel.escuela.services.horario.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService>{

    public HorarioController(HorarioService service) {
        super(service);
    }
}

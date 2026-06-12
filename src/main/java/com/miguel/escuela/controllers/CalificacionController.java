package com.miguel.escuela.controllers;

import com.miguel.escuela.dto.calificaciones.CalificacionRequest;
import com.miguel.escuela.dto.calificaciones.CalificacionResponse;
import com.miguel.escuela.entities.Calificacion;
import com.miguel.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}

package com.banquito.core.pasivo.agenciasud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.pasivo.agenciasud.controller.dto.AgenciaMapper;
import com.banquito.core.pasivo.agenciasud.controller.dto.AgenciaRQ;
import com.banquito.core.pasivo.agenciasud.controller.dto.AgenciaRS;
import com.banquito.core.pasivo.agenciasud.exception.CRUDException;
import com.banquito.core.pasivo.agenciasud.model.Agencia;
import com.banquito.core.pasivo.agenciasud.service.AgenciaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/agencias")
public class AgenciaController {
    
    private AgenciaService service;

    public AgenciaController(AgenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AgenciaRS>> listarTodas() {
        log.info("Retorna las agencias registradas");
        List<Agencia> agencias = this.service.obtenerTodas();
        log.info("Retornando {} agencias", agencias.size());
        return ResponseEntity.ok(AgenciaMapper.mapToAgenciasRS(agencias)); 
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<AgenciaRS> obtenerAgenciaPorCodigo(@PathVariable(name = "codigo") String codigo) {
        log.info("Va a buscar agencia por codigo {}", codigo);
        Agencia agencia = this.service.obtenerPorCodigo(codigo);
        if (agencia!=null) {
            return ResponseEntity.ok(AgenciaMapper.mapToAgenciaRS(agencia));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody AgenciaRQ agenciaRQ) {
        try {
            this.service.crear(AgenciaMapper.mapToAgencia(agenciaRQ));
            return ResponseEntity.ok().build();
        } catch (CRUDException e) {
            log.error("Error al crear la agencia: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public  ResponseEntity actualizar(@RequestBody AgenciaRQ agenciaRQ, @PathVariable(name="id") String id) {
        try {
            this.service.actualizar(id, AgenciaMapper.mapToAgencia(agenciaRQ));
            return ResponseEntity.ok(this.service.obtenerPorId(id));
        } catch (CRUDException e){
            log.error("Error al actualizar la agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).build();
        }
    }


}

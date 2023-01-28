package com.banquito.core.pasivo.agenciasud.controller.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgenciaRS implements Serializable{
    
    private String id;
    private String codigo;
    private String nombre;
}

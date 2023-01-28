package com.banquito.core.pasivo.agenciasud.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "agencias")
public class Agencia {
    
    @Id
    private String id;
    @Indexed(name = "idxu_agencia_codigo", unique = true)
    private String codigo;
    private String nombre;
}

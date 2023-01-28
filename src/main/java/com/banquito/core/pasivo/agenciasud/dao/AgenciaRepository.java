package com.banquito.core.pasivo.agenciasud.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.core.pasivo.agenciasud.model.Agencia;

public interface AgenciaRepository extends MongoRepository<Agencia, String>{
    
    Agencia findByCodigo(String codigo);
}

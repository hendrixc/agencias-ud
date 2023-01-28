package com.banquito.core.pasivo.agenciasud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.pasivo.agenciasud.dao.AgenciaRepository;
import com.banquito.core.pasivo.agenciasud.exception.CRUDException;
import com.banquito.core.pasivo.agenciasud.model.Agencia;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgenciaService {

    private final AgenciaRepository agenciaRepository;

    public AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    public Agencia obtenerPorId(String id) throws CRUDException{
        log.info("Va a recuperar la agencia con id: {}", id);
        Optional<Agencia> agenciaOpt = this.agenciaRepository.findById(id);
        if (!agenciaOpt.isPresent()) {
            throw new CRUDException(404, "No existe la agencia con id: " + id);
        }
        return agenciaOpt.get();
    }

    public Agencia obtenerPorCodigo(String codigo) {
        log.info("Va a recuperar la agencia con codigo: {}", codigo);
        Agencia agencia = this.agenciaRepository.findByCodigo(codigo);
        log.debug("Agencia obtenida para el codigo {} -> {}", codigo, agencia);
        return agencia;
    }

    public List<Agencia> obtenerTodas() {
        log.info("Va a obtener todas las agencias");
        return this.agenciaRepository.findAll();
    }

    public void crear(Agencia agencia) throws CRUDException {
        log.info("Va a crear una agencia");
        log.debug("Datos de la agencia a ser creada: {}", agencia);
        try {
            this.agenciaRepository.save(agencia);
        } catch (Exception e) {
            log.error("Error al crear agencia: {}, con los datos: {}", e.getMessage(), agencia);
            throw new CRUDException(510, "No se pudo crear la agencia, error:" + e.getMessage(), e);
        }
    }

    public void actualizar(String id, Agencia agencia) throws CRUDException {
        log.info("Va a actualizar la agencia con la siguiente informacion: {}", agencia);
        try {
            Optional<Agencia> agenciaOpt = this.agenciaRepository.findById(id);
            if (!agenciaOpt.isPresent()) {
                throw new CRUDException(404, "No existe la agencia con id: " + id);
            }
            Agencia agenciaTmp = agenciaOpt.get();
            agenciaTmp.setCodigo(agencia.getCodigo());
            agenciaTmp.setNombre(agencia.getNombre());
            this.agenciaRepository.save(agenciaTmp);
            log.debug("La agencia {} ha sido actualizada con la siguiente informacion {}", agencia.getId(), agenciaTmp);
        } catch (Exception e) {
            log.error("Error al actualizar la agencia: {}, con los datos: {}", e.getMessage(), agencia);
            throw new CRUDException(520, "No se pudo actualizar la agencia, error:" + e.getMessage(), e);
        }
    }

}

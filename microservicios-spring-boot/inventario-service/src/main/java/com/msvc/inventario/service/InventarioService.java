package com.msvc.inventario.service;

import com.msvc.inventario.dto.InventarioResponse;
import com.msvc.inventario.repository.InventarioRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventarioResponse> isInStock(List<String> codigoSku){
        log.info("Wait started");
        return inventarioRepository.findByCodigoSkuIn(codigoSku).stream()
                .map(inventario ->
                        InventarioResponse.builder()
                                .codigoSku(inventario.getCodigoSku())
                                .inStock(inventario.getCantidad() > 0)
                                .build()
                ).collect(Collectors.toList());
    }
}


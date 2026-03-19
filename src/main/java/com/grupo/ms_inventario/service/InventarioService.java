package com.grupo.ms_inventario.service;

import com.grupo.ms_inventario.model.Inventario;
import com.grupo.ms_inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    // Consultar stock actual
    public Optional<Inventario> consultarStock(String productoId) {
        return inventarioRepository.findById(productoId);
    }

    // Reservar stock (resta 1 si stock > 0)
    @Transactional
    public Map<String, Object> reservarStock(String productoId) {
        Map<String, Object> respuesta = new HashMap<>();

        int filasAfectadas = inventarioRepository.reservarStock(productoId);

        if (filasAfectadas == 0) {
            // Stock insuficiente o producto no existe
            respuesta.put("productoId", productoId);
            respuesta.put("mensaje", "Stock insuficiente");
            respuesta.put("exito", false);
        } else {
            // Releer el stock actualizado
            Inventario inventario = inventarioRepository.findById(productoId).orElseThrow();
            respuesta.put("productoId", productoId);
            respuesta.put("mensaje", "Stock reservado");
            respuesta.put("stockActual", inventario.getStock());
            respuesta.put("exito", true);
        }

        return respuesta;
    }

    // Liberar stock (compensacion Saga: suma 1)
    @Transactional
    public Map<String, Object> liberarStock(String productoId) {
        Map<String, Object> respuesta = new HashMap<>();

        Optional<Inventario> opcional = inventarioRepository.findById(productoId);
        if (opcional.isEmpty()) {
            respuesta.put("productoId", productoId);
            respuesta.put("mensaje", "Producto no encontrado");
            respuesta.put("exito", false);
            return respuesta;
        }

        inventarioRepository.liberarStock(productoId);
        Inventario inventario = inventarioRepository.findById(productoId).orElseThrow();

        respuesta.put("productoId", productoId);
        respuesta.put("mensaje", "Stock liberado (compensacion Saga)");
        respuesta.put("stockActual", inventario.getStock());
        respuesta.put("exito", true);

        return respuesta;
    }
}

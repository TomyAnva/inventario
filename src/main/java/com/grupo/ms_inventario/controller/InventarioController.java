package com.grupo.ms_inventario.controller;

import com.grupo.ms_inventario.model.Inventario;
import com.grupo.ms_inventario.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // GET /inventario/{productoId} — consultar stock actual
    @GetMapping("/{productoId}")
    public ResponseEntity<?> consultarStock(@PathVariable String productoId) {
        Optional<Inventario> inventario = inventarioService.consultarStock(productoId);

        if (inventario.isEmpty()) {
            return ResponseEntity.status(404).body(
                Map.of("mensaje", "Producto no encontrado", "productoId", productoId)
            );
        }

        Inventario inv = inventario.get();
        return ResponseEntity.ok(Map.of(
            "productoId", inv.getIdProducto(),
            "nombre", inv.getNombre(),
            "stockActual", inv.getStock()
        ));
    }

    // POST /inventario/reservar/{productoId} — reservar 1 unidad
    @PostMapping("/reservar/{productoId}")
    public ResponseEntity<?> reservarStock(@PathVariable String productoId) {
        Map<String, Object> resultado = inventarioService.reservarStock(productoId);

        boolean exito = (boolean) resultado.get("exito");
        resultado.remove("exito"); // Limpiar campo interno antes de responder

        if (!exito) {
            // HTTP 400 si stock insuficiente
            return ResponseEntity.badRequest().body(resultado);
        }

        return ResponseEntity.ok(resultado);
    }

    // POST /inventario/liberar/{productoId} — liberar 1 unidad (compensacion Saga)
    @PostMapping("/liberar/{productoId}")
    public ResponseEntity<?> liberarStock(@PathVariable String productoId) {
        Map<String, Object> resultado = inventarioService.liberarStock(productoId);

        boolean exito = (boolean) resultado.get("exito");
        resultado.remove("exito");

        if (!exito) {
            return ResponseEntity.status(404).body(resultado);
        }

        return ResponseEntity.ok(resultado);
    }
}

package com.grupo.ms_inventario.repository;

import com.grupo.ms_inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventarioRepository extends JpaRepository<Inventario, String> {

    // UPDATE condicional: solo resta si stock > 0
    // Retorna el numero de filas afectadas (0 = stock insuficiente)
    @Modifying
    @Query("UPDATE Inventario i SET i.stock = i.stock - 1 WHERE i.idProducto = :idProducto AND i.stock > 0")
    int reservarStock(@Param("idProducto") String idProducto);

    // Liberar stock (compensacion Saga): suma 1
    @Modifying
    @Query("UPDATE Inventario i SET i.stock = i.stock + 1 WHERE i.idProducto = :idProducto")
    int liberarStock(@Param("idProducto") String idProducto);
}

-- =============================================
-- MS Inventario - Schema PostgreSQL
-- Se ejecuta automaticamente al arrancar Spring Boot
-- =============================================

-- Crear tabla solo si no existe (seguro para reinicios)
CREATE TABLE IF NOT EXISTS inventario (
    id_producto VARCHAR(50)  PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    stock       INT          NOT NULL CHECK (stock >= 0)
);

-- =============================================
-- MS Inventario - Datos Iniciales
-- INSERT ... ON CONFLICT DO NOTHING = seguro si ya existe el registro
-- =============================================

INSERT INTO inventario (id_producto, nombre, stock)
VALUES ('PROD-001', 'Boleto VIP', 10)
ON CONFLICT (id_producto) DO NOTHING;

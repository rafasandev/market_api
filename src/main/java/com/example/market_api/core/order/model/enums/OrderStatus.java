package com.example.market_api.core.order.model.enums;

public enum OrderStatus {
    PENDENTE, // Pedido Gerado
    PROCESSANDO, // Pedido em processamento
    CANCELADO, // Pedido cancelado
    PRONTO_RETIRADA, // Aguardando o cliente buscar
    COMPLETADO, // Produto entregue
    EXPIRADO, // Pedido expirado
    AGUARDANDO_CONFIRMACAO, // Agurdando confirmação do vendedor
    SEM_RETIRADA, // Pedido não retirado pelo cliente
    COMPLETADO_EXPIRADO // Produto entregue após expiração
}
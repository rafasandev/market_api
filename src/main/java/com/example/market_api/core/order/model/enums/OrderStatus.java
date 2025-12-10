package com.example.market_api.core.order.model.enums;

public enum OrderStatus {
    PENDENTE, // Pedido Gerado
    PROCESSANDO, // Pedido em processamento
    CANCELADO, // Pedido cancelado
    PAGO, // Pedido Pago
    PRONTO_RETIRADA, // Aguardando o cliente buscar
    COMPLETADO, // Produto entregue
    EXPIRADO, // Pedido expirado
    AGUARDANDO_CONFIRMACAO, // Agurdando confirmação do vendedor
    SEM_RETIRADA // Pedido não retirado pelo cliente
}

// Após a criação do pedido, o status inicial é PENDENTE.
// Apenas nesse momento o cliente pode cancelar o pedido e tornar o status CANCELADO.
// Quando o vendedor inicia o processamento do pedido, o status muda para PROCESSANDO.
// Se o pagamento for feito de forma antecipada (PIX por exemplo), o status muda para PAGO.
// Quando o pedido estiver pronto para ser retirado, o status muda para PRONTO_RETIRADA
// Após a retirada pelo cliente, o status muda para COMPLETADO, sem precisar passar pelo PAGO
// Se o status de PRONTO_RETIRADA permanecer por 24 horas sem que o cliente retire, o status muda para EXPIRADO
// Se o cliente ver que o status PRONTO_RETIRADA está demorando, pode alterar o status para AGUARDANDO_CONFIRMACAO, para que o vendedor seja notificado
// Se o vendedor perceber que o cliente não irá retirar o pedido, pode alterar o status para SEM_RETIRADA

// OBS: Status SEM_RETIRADA é usado para contabilização interna de punição ao cliente que não retirou o pedido, evitando brincadeiras ou fraude no sistema.
package com.example.market_api.core.order.service.use_case;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.market_api.common.exception.BusinessRuleException;
import com.example.market_api.core.order.dto.OrderPickupCodeForm;
import com.example.market_api.core.order.dto.OrderResponseDto;
import com.example.market_api.core.order.mapper.OrderMapper;
import com.example.market_api.core.order.model.Order;
import com.example.market_api.core.order.model.enums.OrderStatus;
import com.example.market_api.core.order.service.OrderService;
import com.example.market_api.core.profile.model.company.CompanyProfile;
import com.example.market_api.core.profile.model.individual.IndividualProfile;
import com.example.market_api.core.profile.service.company.CompanyProfileService;
import com.example.market_api.core.profile.service.individual.IndividualProfileService;
import com.example.market_api.core.user.model.User;
import com.example.market_api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SetOrderCompletedUseCase {

    private final OrderService orderService;
    private final UserService userService;
    private final IndividualProfileService individualProfileService;
    private final CompanyProfileService companyProfileService;

    private OrderMapper orderMapper;

    @Transactional
    public OrderResponseDto updateOrderStatusWithCode(OrderPickupCodeForm pickupCodeForm) {
        User loggedUser = userService.getLoggedInUser();
        CompanyProfile seller = companyProfileService.getById(loggedUser.getId());
        IndividualProfile customer = individualProfileService.getById(pickupCodeForm.getClientId());

        if (customer.getId() != pickupCodeForm.getClientId()) {
            throw new BusinessRuleException("O cliente informado não corresponde ao cliente do pedido.");
        }

        Order requestOrder = orderService.getById(pickupCodeForm.getOrderId());

        if (requestOrder.getCompany().getId() != seller.getId()) {
            throw new BusinessRuleException("O pedido informado não pertence ao vendedor logado.");
        }

        if (pickupCodeForm.getPickupCode() == null ||
                !pickupCodeForm.getPickupCode().equals(requestOrder.getPickUpcode())) {
            throw new BusinessRuleException("O código de retirada informado é inválido.");
        }

        if (pickupCodeForm.getNewStatus() != OrderStatus.COMPLETADO) {
            throw new BusinessRuleException("O status informado não corresponde ao status de PEDIDO COMPLETADO.");
        }

        if (requestOrder.getStatus() != OrderStatus.PRONTO_RETIRADA) {
            throw new BusinessRuleException(
                    "O pedido não está pronto para retirada. Espere a alteração do status do pedido para PRONTO PARA RETIRADA");
        }

        // Atualização do saldo do vendedor
        BigDecimal orderTotal = requestOrder.getOrderTotal();
        BigDecimal sellerBalance = seller.getBalance();
        seller.setBalance(sellerBalance.add(orderTotal));
        companyProfileService.save(seller);

        requestOrder.setStatus(OrderStatus.COMPLETADO);
        Order updatedOrder = orderService.save(requestOrder);
        return orderMapper.toResponseDto(updatedOrder);
    }
}

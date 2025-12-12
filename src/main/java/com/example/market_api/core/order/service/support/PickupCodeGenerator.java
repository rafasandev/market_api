package com.example.market_api.core.order.service.support;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.example.market_api.common.exception.UserRuleException;
import com.example.market_api.core.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PickupCodeGenerator {

    private static final String CHAR_POOL = "23456789ACDEFGHJKMNPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 5;
    private static final int MAX_ATTEMPTS = 10;
    
    private final OrderService orderService;
    private final SecureRandom random = new SecureRandom();

    public String generateUniqueCode() {
        int attempts = 0;
        String code;

        do {
            code = buildCode();
            attempts++;
            
            if (attempts >= MAX_ATTEMPTS) {
                throw new UserRuleException(
                    "Não foi possível gerar código único de retirada. Tente novamente."
                );
            }
        } while (orderService.existsByPickupCode(code));

        return code;
    }

    private String buildCode() {
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH + 1);
        codeBuilder.append("#");
        
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHAR_POOL.length());
            codeBuilder.append(CHAR_POOL.charAt(randomIndex));
        }
        
        return codeBuilder.toString();
    }
}

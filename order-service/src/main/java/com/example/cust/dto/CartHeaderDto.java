package com.example.cust.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartHeaderDto {

    // DB의 cart_id (auto_increment)
    private Integer cartId;

    // 고객 ID
    private Integer customerId;

    // 생성 시각 (DB: created_at datetime(6))
    private LocalDateTime createdAt;

    // 장바구니 항목 리스트 (CartItemDto는 CartItem Entity 역할)
    @Builder.Default
    private List<CartItemDto> cartItems = new ArrayList<>();

    // 장바구니 헤더 ID 생성기 (임시)
    private static final AtomicInteger CART_HEADER_ID_GENERATOR = new AtomicInteger(1);

    // 임시 ID 할당 및 생성 시각 설정
    public static CartHeaderDto createNew(Integer customerId) {
        return CartHeaderDto.builder()
                .cartId(CART_HEADER_ID_GENERATOR.getAndIncrement())
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .cartItems(new ArrayList<>())
                .build();
    }
}
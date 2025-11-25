package com.example.cust.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_option_id")
    private Integer cartOptionId;

    // 연관 관계: CartOption(N) <-> CartItem(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @Column(name = "option_id", nullable = false)
    private Integer optionId;

    @Column(name = "option_price", nullable = false)
    private Integer optionPrice; // 장바구니 담을 시점의 옵션 가격
}
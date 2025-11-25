package com.example.cust.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    // 장바구니 항목 식별자 (DB의 cart_item_id에 해당)
    private Integer cartItemId;

    // 메뉴 정보
    private String menuCode;
    private String menuName;
    private Integer unitPrice; // 메뉴 기본 가격
    private Integer quantity;  // 수량

    // 옵션 정보 (표시를 위해 List<String>으로 단순화)
    private List<String> options;

    // 옵션이 포함된 항목의 최종 가격
    private Integer totalItemPrice;
}
package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal; // Decimal 타입 매핑을 위해 추가

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto {

    // 1. option_id (Entity의 id 필드)
    private Integer optionId;

    // 2. option_group_name
    private String optionGroupName;

    // 3. option_name
    private String optionName;

    // 4. default_price
    private Integer defaultPrice;

    // 5. changing_material
    private String changingMaterial;

    // 6. quantity
    private BigDecimal quantity;

    // 7. unit
    private String unit;

    // 8. process_method
    private String processMethod;


    // 헬퍼 메서드: 가격 변동분이 필요할 경우 사용 (현재 DTO에 price 필드가 없으므로 defaultPrice 사용)
    public int getPriceDelta() {
        // 옵션의 추가 가격 필드(price)가 Entity에 없으므로,
        // 임시로 defaultPrice를 추가 가격으로 가정하고 반환합니다.
        return (this.defaultPrice != null) ? this.defaultPrice : 0;
    }
}
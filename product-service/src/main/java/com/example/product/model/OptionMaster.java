package com.example.product.model;

import com.example.product.dto.OptionDto; // DTO 변환을 위해 임시로 추가
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "option_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // option_id (PK, auto_increment)에 매핑

    // 🌟 요청하신 옵션 그룹 이름 추가
    @Column(name = "option_group_name", length = 50, nullable = true) // DB 스키마: YES
    private String optionGroupName;

    @Column(name = "option_name", length = 100, nullable = false) // DB 스키마: NO
    private String optionName;

    @Column(name = "default_price", nullable = false) // DB 스키마: yes
    private Integer defaultPrice; // int 타입 매핑

    @Column(name = "changing_material", length = 100, nullable = true) // DB 스키마: YES
    private String changingMaterial;

    @Column(name = "quantity", precision = 8, scale = 2, nullable = true) // DB 스키마: YES
    private BigDecimal quantity;

    @Column(name = "unit", length = 10, nullable = true) // DB 스키마: YES
    private String unit;

    @Column(name = "process_method", length = 20, nullable = true) // DB 스키마: YES
    private String processMethod;


    // 💡 Service에서 DTO로 변환하기 위한 헬퍼 메서드를 Entity에 추가하는 것이 일반적입니다.
    public OptionDto toDto() {
        return new OptionDto(
                this.id, // Long -> Integer로 변환 (OptionDto에 맞춤)
                this.optionGroupName,
                this.optionName,
                this.defaultPrice,
                this.changingMaterial,
                this.quantity,
                this.unit,
                this.processMethod
        );
    }
}
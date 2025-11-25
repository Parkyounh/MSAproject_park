package com.example.product.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Menu 엔티티의 ID를 참조
    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    // OptionMaster 엔티티의 ID를 참조
    @Column(name = "option_id", nullable = false)
    private Long optionId;

    // 추가 필드: 해당 옵션이 필수인지 선택인지 등을 정의할 수 있습니다.
}
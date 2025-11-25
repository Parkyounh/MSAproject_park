package com.example.product.repository;


import com.example.product.model.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

    /**
     * 특정 메뉴 ID에 연결된 모든 옵션 연결 정보 목록 조회
     */
    List<MenuOption> findByMenuId(Long menuId);
}
package com.example.product.service;



import com.example.product.dto.OptionDto;
import com.example.product.model.Menu;
import com.example.product.model.OptionMaster;
import com.example.product.repository.MenuRepository;
import com.example.product.repository.OptionMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OptionMasterRepository optionMasterRepository;

    /**
     * 모든 메뉴 항목을 조회합니다.
     */
    public List<Menu> getAllMenus() {
        return menuRepository.findAllByOrderByCategoryAscMenuNameAsc();
    }

    /**
     * 네비게이션에 사용할 모든 고유 카테고리 목록을 조회합니다.
     */
    public List<String> getAllCategories() {
        // DB에서 조회된 카테고리 목록을 반환
        return menuRepository.findAllCategories();
    }

    public List<Menu> getMenusByCategory(String category) {
        return menuRepository.findByCategory(category);
    }

    public List<OptionDto> getAllOptions() {

        // 1. DB에서 모든 OptionMaster Entity를 조회합니다.
        List<OptionMaster> entities = optionMasterRepository.findAll();

        // 2. Entity 리스트를 Stream으로 변환하고, 각 Entity의 toDto() 메서드를 호출하여 OptionDto로 매핑합니다.
        return entities.stream()
                .map(OptionMaster::toDto) // OptionMaster Entity에 정의된 toDto() 메서드 사용
                .collect(Collectors.toList());
    }
}
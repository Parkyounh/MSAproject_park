package com.example.cust.controller;

import com.example.cust.dto.CartItemDto;
import com.example.cust.dto.CartHeaderDto;
import com.example.cust.dto.OrderSummaryDto;
import com.example.cust.model.CartHeader;
import com.example.cust.service.MakeCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final MakeCart makeCartService;

    @GetMapping("")
    public String home(Model model) {

        return "home";
    }

    // 2. 장바구니 항목 추가 요청 (POST /orders/cart/add)
    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDto receivedDto) {

        // 🚨 1. 사용자 ID 획득 (실제로는 Security Context에서 가져옴)
        // int customerId = authentication.getUserId();
        int customerId = 1; // 임시 고객 ID

        // 2. Service 호출: 장바구니 헤더 조회/생성 및 항목 추가/저장
        CartHeader cartHeader = makeCartService.getOrCreateCartHeader(customerId);
        makeCartService.addItemToCart(cartHeader, receivedDto);

        System.out.println("--- [장바구니 항목 추가 요청 받음] ---");
        System.out.println("받은 항목: " + receivedDto);
        System.out.println("--------------------------------");

        // 성공 응답 반환
        return new ResponseEntity<>(Map.of("message", "장바구니에 성공적으로 추가되었습니다. (Service 호출 시뮬레이션)"), HttpStatus.OK);
    }

    // 3. 장바구니 페이지 조회 (GET /orders/cart)
    @GetMapping("/cart")
    public String viewCart(Model model) {

        // 🌟 Service 호출: 고객 ID로 CartItem 목록을 가져오는 로직 시뮬레이션 🌟
        // int customerId = authentication.getUserId();

        // TODO: List<CartItemDto> cartItems = makeCartService.getCartItems(customerId); 호출

        // --- (Service 호출 전까지) 임시 더미 데이터로 대체 ---
        List<CartItemDto> cartItems = Arrays.asList(
                new CartItemDto(1, "ADE-001", "아메리카노 (Ice)", 4000, 2,
                        Arrays.asList("Grande (+1,000)", "샷 추가 (+500)"), 11000),
                new CartItemDto(2, "CFE-002", "카페 라떼 (Hot)", 5000, 1,
                        Arrays.asList("Tall", "바닐라 시럽 (+500)"), 5500)
        );

        // --- 금액 계산 로직 (더미 데이터 기준) ---
        int subtotal = 0;
        for (CartItemDto item : cartItems) {
            if (item.getTotalItemPrice() != null) {
                subtotal += item.getTotalItemPrice();
            }
        }

        int deliveryFee = 3000;
        int finalTotal = subtotal + deliveryFee;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalTotal", finalTotal);

        return "cart"; // cart.html 템플릿 반환
    }


    // 4. 주문/결제 처리 요청
    @PostMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes) {

        // int customerId = authentication.getUserId();
        // TODO: makeCartService.checkout(customerId); // 장바구니 데이터를 주문 테이블로 이동 및 장바구니 비우기

        int createdOrderId = 1002;
        redirectAttributes.addFlashAttribute("orderId", createdOrderId);

        return "redirect:/orders/success";
    }

    @GetMapping("/{orderId}")
    public String viewOrderDetail(@PathVariable Integer orderId, Model model) {

        // --- 실제 로직: orderId로 OrderHeader, OrderItem, OrderOption 조회 ---

        // 1. 주문 개요 더미 데이터
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDate", LocalDateTime.now().minusMinutes(30));
        model.addAttribute("totalAmount", 25500);
        model.addAttribute("status", "제조 대기");

        // 2. 주문 항목 목록 (이전에 사용한 CartItemDto를 재활용하여 사용)
        List<CartItemDto> orderItems = Arrays.asList(
                new CartItemDto(1, "ADE-001", "아메리카노 (Ice)", 4000, 2,
                        Arrays.asList("Grande (+1,000)", "샷 추가 (+500)"), 11000), // 2잔
                new CartItemDto(2, "CFE-002", "카페 라떼 (Hot)", 5000, 3,
                        Arrays.asList("Tall", "두유 변경 (+0)", "바닐라 시럽 (+500)"), 16500) // 3잔
        );

        model.addAttribute("orderItems", orderItems);

        return "order-detail"; // order-detail.html 템플릿 반환
    }
}
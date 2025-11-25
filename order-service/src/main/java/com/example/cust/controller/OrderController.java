package com.example.cust.controller;


import com.example.cust.dto.CartItemDto;
import com.example.cust.dto.OrderSummaryDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    // 1. 장바구니 페이지 조회
    @GetMapping("/cart")
    public String viewCart(Model model) {
        // --- 실제 로직: customerId로 CartHeader, CartItem, CartOption 조회 ---

        // 현재는 더미 데이터 사용
        List<CartItemDto> cartItems = Arrays.asList(
                new CartItemDto(1,"ADE-001", "아메리카노 (Ice)", 4000, 1,
                        Arrays.asList("Grande (+1,000)", "샷 추가 (+500)"), 5500),
                new CartItemDto(2,"CFE-002", "카페 라떼 (Hot)", 5000, 2,
                        Arrays.asList("Tall", "두유 변경 (+0)"), 10000)
        );

        int subtotal = 15500;
        int deliveryFee = 3000;
        int finalTotal = subtotal + deliveryFee;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalTotal", finalTotal);

        return "cart"; // cart.html 템플릿 반환
    }

    // 2. 주문/결제 처리 요청
    @PostMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes) {
        // --- 실제 로직: 장바구니 데이터를 읽어 orders 테이블로 전환 및 재고 차감 ---

        // 주문 성공 후 주문 ID를 리다이렉트에 전달
        int createdOrderId = 1002;
        redirectAttributes.addFlashAttribute("orderId", createdOrderId);

        return "redirect:/orders/success";
    }

    // 3. 주문 완료 페이지 조회
    @GetMapping("/success")
    public String orderSuccess(@ModelAttribute("orderId") Integer orderId, Model model) {
        if (orderId == null) {
            // 주문 ID가 없는 경우 (직접 URL 접근 등)
            return "redirect:/orders/cart";
        }

        // --- 실제 로직: orderId로 OrderSummaryDto 조회 ---

        // 더미 데이터
        OrderSummaryDto summary = new OrderSummaryDto(
                orderId, 18500, LocalDateTime.now(), "결제 완료"
        );

        model.addAttribute("summary", summary);
        return "order-success"; // order-success.html 템플릿 반환
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
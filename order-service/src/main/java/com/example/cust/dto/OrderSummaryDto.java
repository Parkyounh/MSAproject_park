package com.example.cust.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryDto {

    private Integer orderId;
    private Integer totalAmount;
    private LocalDateTime orderDate;
    private String status;

}
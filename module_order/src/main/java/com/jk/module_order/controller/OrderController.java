package com.jk.module_order.controller;

import com.jk.module_order.common.dto.ApiResponseDto;
import com.jk.module_order.dto.request.CreateOrderRequest;
import com.jk.module_order.dto.response.OrderResponse;
import com.jk.module_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{orderNum}")
    public ResponseEntity<ApiResponseDto<OrderResponse>> getOrderDetails(@PathVariable Long orderNum) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderNum);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "주문 정보 조회 결과", orderResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<OrderResponse>> create(@RequestBody CreateOrderRequest createOrderRequest, @RequestHeader(value = "X-USER-ID") Long userId) {
        OrderResponse orderResponse = orderService.create(createOrderRequest, userId);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.CREATED, "주문 진입", orderResponse));
    }

    @DeleteMapping("/{orderNum}")
    public ResponseEntity<ApiResponseDto<OrderResponse>> delete(@PathVariable Long orderNum) {
        OrderResponse orderResponse = orderService.delete(orderNum);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "주문 취소", orderResponse));
    }


}

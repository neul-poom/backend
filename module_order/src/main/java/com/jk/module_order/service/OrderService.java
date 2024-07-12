package com.jk.module_order.service;

import com.jk.module_order.common.exception.CustomException;
import com.jk.module_order.common.exception.ErrorCode;
import com.jk.module_order.dto.request.CreateOrderRequest;
import com.jk.module_order.dto.response.OrderResponse;
import com.jk.module_order.entity.Order;
import com.jk.module_order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderResponse getOrderDetails(Long orderNum) {
        Order targetOrder = orderRepository.findById(orderNum)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        return new OrderResponse(targetOrder.getOrderNum(), targetOrder.getBuyerNum(), targetOrder.getLectureNum(), targetOrder.getPrice(), targetOrder.getStatus());
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest createOrderRequest, Long userId) {

        Order order = Order.create(createOrderRequest, userId);

        Order savedOrder = orderRepository.save(order);

        savedOrder.updateStatus(Order.OrderStatus.IN_PROGRESS);

        return new OrderResponse(savedOrder.getOrderNum(), savedOrder.getBuyerNum(), savedOrder.getLectureNum(), savedOrder.getPrice(), savedOrder.getStatus());
    }

    @Transactional
    public OrderResponse delete(Long orderNum) {
        Order targetOrder = orderRepository.findById(orderNum)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if (targetOrder.getStatus().equals(Order.OrderStatus.CANCELED)) {
            throw new CustomException(ErrorCode.CANCELED_ORDER);
        }

        if (targetOrder.getStatus().equals(Order.OrderStatus.FAILED_CUSTOMER)) {
            throw new CustomException(ErrorCode.FAILED_ORDER);
        }

        targetOrder.delete();

        return new OrderResponse(targetOrder.getOrderNum(), targetOrder.getBuyerNum(), targetOrder.getLectureNum(), targetOrder.getPrice(), targetOrder.getStatus());
    }

    @Transactional
    public OrderResponse failedByCustomer(Long orderNum) {
        Order targetOrder = orderRepository.findById(orderNum)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if (targetOrder.getStatus().equals(Order.OrderStatus.CANCELED)) {
            throw new CustomException(ErrorCode.CANCELED_ORDER);
        }

        if (targetOrder.getStatus().equals(Order.OrderStatus.FAILED_CUSTOMER)) {
            throw new CustomException(ErrorCode.FAILED_ORDER);
        }

        targetOrder.failed();

        return new OrderResponse(targetOrder.getOrderNum(), targetOrder.getBuyerNum(), targetOrder.getLectureNum(), targetOrder.getPrice(), targetOrder.getStatus());
    }

    @Transactional
    public OrderResponse completeOrder(Long orderNum) {
        Order targetOrder = orderRepository.findById(orderNum)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if (targetOrder.getStatus().equals(Order.OrderStatus.CANCELED)) {
            throw new CustomException(ErrorCode.CANCELED_ORDER);
        }

        if (targetOrder.getStatus().equals(Order.OrderStatus.FAILED_CUSTOMER)) {
            throw new CustomException(ErrorCode.FAILED_ORDER);
        }

        targetOrder.complete();

        return new OrderResponse(targetOrder.getOrderNum(), targetOrder.getBuyerNum(), targetOrder.getLectureNum(), targetOrder.getPrice(), targetOrder.getStatus());
    }
}

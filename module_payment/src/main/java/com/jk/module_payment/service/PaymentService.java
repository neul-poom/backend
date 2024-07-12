package com.jk.module_payment.service;

import com.jk.module_payment.client.OrderClient;
import com.jk.module_payment.client.dto.response.OrderResponse;
import com.jk.module_payment.common.enums.OrderStatus;
import com.jk.module_payment.common.exception.CustomException;
import com.jk.module_payment.common.exception.ErrorCode;
import com.jk.module_payment.dto.request.CreatePaymentRequestDto;
import com.jk.module_payment.dto.response.PaymentResponseDto;
import com.jk.module_payment.entity.Payment;
import com.jk.module_payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public PaymentResponseDto getPaymentdetails(Long orderNum) {
        Payment payment = paymentRepository.findById(orderNum)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        return new PaymentResponseDto(
                payment.getPaymentNum(),
                payment.getOrderNum(),
                payment.getBuyerNum(),
                payment.getPrice(),
                payment.getCreatedAt()
        );
    }

    @Transactional
    public PaymentResponseDto createPayment(CreatePaymentRequestDto createPaymentRequestDto, Long userId) {

        OrderResponse orderResponse = orderClient.getOrderDetails(createPaymentRequestDto.orderNum());
        if(orderResponse.status() != OrderStatus.IN_PROGRESS) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        double chance = Math.random();
        if (chance < 0.2) {
            orderClient.failedByCustomer(createPaymentRequestDto.orderNum());
            throw new CustomException(ErrorCode.FAILED_CUSTOMER);
        }

        OrderResponse completeOrderResponse = orderClient.completeOrder(createPaymentRequestDto.orderNum());

        Payment payment = Payment.create(completeOrderResponse.orderNum(), completeOrderResponse.buyerNum(), completeOrderResponse.price());

//        log.info("payment 생성 : {}", payment);
//        log.info("payment id : {}", payment.getOrderNum());

        Payment newPayment = paymentRepository.save(payment);

//        log.info("payment 저장 : {}", newPayment);

        return new PaymentResponseDto(newPayment.getPaymentNum(), newPayment.getOrderNum(), newPayment.getBuyerNum(), newPayment.getPrice(), newPayment.getCreatedAt());
    }


    @Transactional
    public void delete(Long paymentNum) {
        Payment targetPayment = paymentRepository.findById(paymentNum)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if(targetPayment.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.DELETED_PAYMENT);
        }

        orderClient.cancelOrder(targetPayment.getOrderNum());

        targetPayment.delete();
    }
}

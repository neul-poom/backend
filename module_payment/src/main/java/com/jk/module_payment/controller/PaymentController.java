package com.jk.module_payment.controller;

import com.jk.module_payment.common.dto.ApiResponseDto;
import com.jk.module_payment.dto.request.CreatePaymentRequestDto;
import com.jk.module_payment.dto.response.PaymentResponseDto;
import com.jk.module_payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody CreatePaymentRequestDto createPaymentRequest, @RequestHeader(value = "X-USER-ID") Long userId){
        PaymentResponseDto response = paymentService.createPayment(createPaymentRequest, userId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{orderNum}")
    public ResponseEntity<ApiResponseDto<PaymentResponseDto>> getPaymentdetails(@PathVariable Long orderNum){
        PaymentResponseDto paymentDetailsResponse = paymentService.getPaymentdetails(orderNum);


        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "결제 정보", paymentDetailsResponse));
    }

    @DeleteMapping("/{orderNum}")
    public ResponseEntity<ApiResponseDto<?>> delete(@PathVariable Long orderNum) {

        paymentService.delete(orderNum);

        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "결제 취소 성공", null));
    }
}

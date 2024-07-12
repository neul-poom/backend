package com.jk.module_payment.client;

import com.jk.module_payment.client.dto.response.OrderResponse;
import com.jk.module_payment.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "orderClient", url = "${feign.orderClient.url}", configuration = FeignConfig.class)
public interface OrderClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/internal/orders/cancel/{orderNum}")
    OrderResponse cancelOrder(@PathVariable("orderNum") Long orderNum);

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/internal/orders/{orderNum}")
    OrderResponse getOrderDetails(@PathVariable("orderNum") Long orderNum);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/internal/orders/fail/{orderNum}")
    OrderResponse failedByCustomer(@PathVariable("orderNum") Long orderNum);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/internal/orders/complete/{orderNum}")
    OrderResponse completeOrder(@PathVariable("orderNum") Long orderNum);
}

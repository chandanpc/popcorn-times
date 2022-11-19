package com.dailycodebuffer.OrderService.Service;

import com.dailycodebuffer.OrderService.entity.Order;
import com.dailycodebuffer.OrderService.external.clients.ProductService;
import com.dailycodebuffer.OrderService.model.OrderRequest;
import com.dailycodebuffer.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        Order order=Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .orderStatus("Done")
                .amount(orderRequest.getTotalAmount())
                .build();

        orderRepository.save(order);
        log.info("object Saved---"+order.getId());
        return order.getId();

    }
}

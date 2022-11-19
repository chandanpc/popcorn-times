package com.dailycodebuffer.OrderService.Service;

import com.dailycodebuffer.OrderService.model.OrderRequest;

public interface OrderService {

    long placeOrder(OrderRequest orderRequest);
}

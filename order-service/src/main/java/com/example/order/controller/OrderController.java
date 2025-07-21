package com.example.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.model.Order;
import com.example.order.service.OrderService;

@RestController
@RequestMapping("api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody Order request){
		//validate order
		orderService.createOrder(request);
		return ResponseEntity.ok("Order request placed successfully");
	}

}

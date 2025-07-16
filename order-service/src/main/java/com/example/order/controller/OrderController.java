package com.example.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.model.Order;

@RestController
@RequestMapping("api/orders")
public class OrderController {
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody Order request){
		//validate order
		
		return ResponseEntity.ok("Order placed successfully");
	}

}

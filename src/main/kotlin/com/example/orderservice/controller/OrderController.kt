package com.example.orderservice.controller

import com.example.orderservice.model.Order
import com.example.orderservice.model.OrderStatus
import com.example.orderservice.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(orderService.getOrderById(id))
    }
    
    @GetMapping("/customer/{customerId}")
    fun getOrdersByCustomer(@PathVariable customerId: Long): ResponseEntity<List<Order>> {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId))
    }
    
    @PostMapping
    fun createOrder(@RequestBody order: Order): ResponseEntity<Order> {
        val createdOrder = orderService.createOrder(order)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder)
    }
    
    @PutMapping("/{id}/status")
    fun updateOrderStatus(
        @PathVariable id: Long,
        @RequestParam status: OrderStatus
    ): ResponseEntity<Order> {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status))
    }
    
    @PostMapping("/{id}/cancel")
    fun cancelOrder(@PathVariable id: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(orderService.cancelOrder(id))
    }
}
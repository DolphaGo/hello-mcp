package com.example.orderservice.service

import com.example.orderservice.model.Order
import com.example.orderservice.model.OrderStatus
import com.example.orderservice.repository.OrderRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository) {

    fun getOrderById(id: Long): Order {
        return orderRepository.findById(id)
            .orElseThrow { NoSuchElementException("Order not found with id: $id") }
    }
    
    fun getOrdersByCustomerId(customerId: Long): List<Order> {
        return orderRepository.findByCustomerId(customerId)
    }
    
    @Transactional
    fun createOrder(order: Order): Order {
        // Calculate total amount from items
        val totalAmount = order.items.sumOf { it.subtotal }
        
        // Set order reference in items
        order.items.forEach { item -> item.order = order }
        
        // Create new order with calculated total
        val newOrder = order.copy(totalAmount = totalAmount)
        
        return orderRepository.save(newOrder)
    }
    
    @Transactional
    fun updateOrderStatus(id: Long, status: OrderStatus): Order {
        val order = getOrderById(id)
        val updatedOrder = order.copy(status = status)
        return orderRepository.save(updatedOrder)
    }
    
    @Transactional
    fun cancelOrder(id: Long): Order {
        val order = getOrderById(id)
        
        if (order.status == OrderStatus.SHIPPED || order.status == OrderStatus.DELIVERED) {
            throw IllegalStateException("Cannot cancel order with status: ${order.status}")
        }
        
        val cancelledOrder = order.copy(status = OrderStatus.CANCELLED)
        return orderRepository.save(cancelledOrder)
    }
}
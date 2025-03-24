package com.example.orderservice.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    val customerId: Long,
    
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "order")
    val items: MutableList<OrderItem> = mutableListOf(),
    
    val totalAmount: BigDecimal = BigDecimal.ZERO,
    
    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.CREATED,
    
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class OrderStatus {
    CREATED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}
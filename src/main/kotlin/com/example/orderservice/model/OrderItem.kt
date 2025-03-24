package com.example.orderservice.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    var order: Order? = null,
    
    val productId: Long,
    
    val quantity: Int,
    
    val price: BigDecimal,
    
    val subtotal: BigDecimal = price.multiply(BigDecimal(quantity))
)
package com.mentormate.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "restaurant_table_id")
    @NotNull(message = "Restaurant Table cannot be null!")
    private RestaurantTable restaurantTable;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User cannot be null!")
    private User user;

    @NotNull(message = "Date cannot be null!")
    @Column(name = "create_date")
    private OffsetDateTime createDate;

    @OneToOne
    @JoinColumn(name = "order_status_id")
    @NotNull(message = "Order Status cannot be null!")
    private OrderStatus orderStatus;

    @Formula("(SELECT sum(p.price * op.quantity)" +
             " FROM orders o" +
             " JOIN order_products op ON op.order_id = o.id" +
             " JOIN products p ON op.product_id = p.id" +
             " WHERE op.order_id = id)")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;
}

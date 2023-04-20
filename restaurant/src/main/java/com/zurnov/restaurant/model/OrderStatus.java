package com.zurnov.restaurant.model;

import com.zurnov.restaurant.model.enumeration.OrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_statuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus extends BaseEntity {

    @NotNull(message = "Status type cannot be null!")
    @Enumerated(EnumType.STRING)
    @Column(name = "status_type")
    private OrderEnum orderEnum;
}

package com.mentormate.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "restaurant_tables")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTable extends BaseEntity {

    @NotNull(message = "Restaurant table number cannot be null!")
    @Column(name = "restaurant_table_number")
    private int tableNumber;
}

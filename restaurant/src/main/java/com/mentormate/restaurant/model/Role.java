package com.mentormate.restaurant.model;

import com.mentormate.restaurant.model.enumeration.RoleEnum;
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
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    @NotNull(message = "Role type cannot be null!")
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleEnum roleEnum;

}

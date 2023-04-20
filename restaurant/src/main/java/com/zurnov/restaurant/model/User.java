package com.zurnov.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @NotNull(message = "Name cannot be null!")
    @Size(max = 100, message = "Name cannot be longer than 100 symbols!")
    @Column(name = "name", length = 100)
    private String name;

    @NotNull(message = "Email cannot be null!")
    @Size(max = 255, message = "Email cannot be longer than 255 symbols!")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Password cannot be null!")
    @Column(name = "password")
    private String password;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> userRoles;

}

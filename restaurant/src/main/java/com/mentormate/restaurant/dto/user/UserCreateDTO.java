package com.mentormate.restaurant.dto.user;

import com.mentormate.restaurant.dto.role.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO implements Serializable {

    @NotNull(message = "Name cannot be null!")
    @Size(max = 100, message = "Name cannot be longer than 100 symbols!")
    private String name;

    @NotNull(message = "Email cannot be null!")
    @Size(max = 255, message = "Email cannot be longer than 255 symbols!")
    private String email;

    @NotNull(message = "Password cannot be null!")
    private String password;
    
    private Boolean isDeleted;

    private List<RoleDTO> roles;
}

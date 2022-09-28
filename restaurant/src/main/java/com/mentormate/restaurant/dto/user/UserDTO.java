package com.mentormate.restaurant.dto.user;

import com.mentormate.restaurant.dto.BaseDTO;
import com.mentormate.restaurant.dto.role.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO {

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

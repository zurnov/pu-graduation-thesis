package com.zurnov.restaurant.dto.role;

import com.zurnov.restaurant.dto.BaseDTO;
import com.zurnov.restaurant.model.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends BaseDTO {

    @NotNull(message = "Role type cannot be null!")
    private RoleEnum roleEnum;
}

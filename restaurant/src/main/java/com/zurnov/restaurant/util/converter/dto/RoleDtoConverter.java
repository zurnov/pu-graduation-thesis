package com.zurnov.restaurant.util.converter.dto;

import com.zurnov.restaurant.dto.role.RoleDTO;
import com.zurnov.restaurant.model.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleDtoConverter {

    /**
     * Converts the role Entity to DTO Object
     *
     * @param role Entity to be converted
     * @return roleDTO the converted DTO
     */
    public static RoleDTO convertEntityToDto(Role role) {
        
        if (role == null) {
            return null;
        }
        
        RoleDTO roleDTO = new RoleDTO();
        
        roleDTO.setId(role.getId());
        roleDTO.setRoleEnum(role.getRoleEnum());
        
        return roleDTO;
    }

    /**
     * Converts the roleDTO DTO Object to Entity
     *
     * @param roleDTO to be converted
     * @return role the converted Entity
     */
    public static Role convertDtoToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        
        role.setId(roleDTO.getId());
        role.setRoleEnum(roleDTO.getRoleEnum());
        
        return role;
    }
}

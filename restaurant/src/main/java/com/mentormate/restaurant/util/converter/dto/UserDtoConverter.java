package com.mentormate.restaurant.util.converter.dto;

import com.mentormate.restaurant.dto.role.RoleDTO;
import com.mentormate.restaurant.dto.user.UserCreateDTO;
import com.mentormate.restaurant.dto.user.UserDTO;
import com.mentormate.restaurant.model.Role;
import com.mentormate.restaurant.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserDtoConverter {

    /**
     * Converts the user Entity to DTO Object
     *
     * @param user Entity to be converted
     * @return userDTO the converted DTO
     */
    public static UserDTO convertEntityToDto(User user) {
        
        if (user == null) {
            return null;
        }
        
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsDeleted(user.getIsDeleted());
        userDTO.setPassword(user.getPassword());

        List<RoleDTO> roles = user.getUserRoles()
                .stream()
                .map(RoleDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
        userDTO.setRoles(roles);

        return userDTO;
    }

    /**
     * Converts the userDTO DTO Object to Entity
     *
     * @param userDTO to be converted
     * @return user the converted Entity
     */
    public static User convertDtoToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setIsDeleted(userDTO.getIsDeleted());
        user.setPassword(userDTO.getPassword());

        List<Role> roles = userDTO.getRoles()
                .stream()
                .map(RoleDtoConverter::convertDtoToEntity)
                .collect(Collectors.toList());
        user.setUserRoles(roles);

        return user;
    }

    /**
     * Converts the userCreateDTO DTO Object to Entity
     *
     * @param userCreateDTO to be converted
     * @return user the converted Entity
     */
    public static User convertDtoToEntity(UserCreateDTO userCreateDTO) {
        User user = new User();

        user.setName(userCreateDTO.getName());
        user.setEmail(userCreateDTO.getEmail());
        user.setIsDeleted(userCreateDTO.getIsDeleted());
        user.setPassword(userCreateDTO.getPassword());

        List<Role> roles = userCreateDTO.getRoles()
                .stream()
                .map(RoleDtoConverter::convertDtoToEntity)
                .collect(Collectors.toList());
        user.setUserRoles(roles);

        user.setIsDeleted(false);

        return user;
    }
}

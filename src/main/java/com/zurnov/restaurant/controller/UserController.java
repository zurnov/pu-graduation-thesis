package com.zurnov.restaurant.controller;

import com.zurnov.restaurant.dto.user.UserCreateDTO;
import com.zurnov.restaurant.dto.user.UserDTO;
import com.zurnov.restaurant.model.User;
import com.zurnov.restaurant.model.enumeration.RoleEnum;
import com.zurnov.restaurant.service.UserService;
import com.zurnov.restaurant.util.PathHelper;
import com.zurnov.restaurant.util.converter.dto.UserDtoConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PathHelper.BASE_PATH + PathHelper.USER_PATH)
@Validated
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @Operation(
            tags = "User",
            operationId = "getUserById",
            summary = "get User object by id",
            description = "Finds and returns User" +
                    " or else null if none of them matches the user id",
            parameters = @Parameter(
                    name = "userId",
                    description = "user id with which we want to filter all users",
                    example = "1"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public UserDTO getUserById(@PathVariable int userId) {
        User user = userService.findById(userId);
        return UserDtoConverter.convertEntityToDto(user);
    }

    @GetMapping("/email")
    @Operation(
            tags = "User",
            operationId = "getUserByEmail",
            summary = "get User object by user email",
            description = "Finds and returns User that corresponds to the email" +
                    " or else list of all Users",
            parameters = @Parameter(
                    name = "email",
                    description = "email with which we want to filter all users"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public List<UserDTO> getUserByEmail(@RequestParam(required = false) String email) {
        List<User> users = userService.findByEmail(email);

        return users.stream()
                .map(UserDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/name")
    @Operation(
            tags = "User",
            operationId = "getUserByName",
            summary = "get User object by user name",
            description = "Finds and returns User that corresponds to the name" +
                    " or else list of all Users",
            parameters = @Parameter(
                    name = "name",
                    description = "name with which we want to filter all users"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public List<UserDTO> getUserByName(@RequestParam(required = false) String name) {
        List<User> users = userService.findByName(name);

        return users.stream()
                .map(UserDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/role")
    @Operation(
            tags = "User",
            operationId = "getUserByRole",
            summary = "get User object by user role",
            description = "Finds and returns User that corresponds to the role" +
                    " or else list of all Users",
            parameters = @Parameter(
                    name = "roles",
                    description = "roles with which we want to filter all users"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public List<UserDTO> getUserByRole(@RequestParam(required = false) List<RoleEnum> roles) {
        List<User> users = userService.findByRoles(roles);

        return users.stream()
                .map(UserDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping()
    @Operation(
            tags = "User",
            operationId = "createUser",
            summary = "create User",
            description = "Saves and returns the saved User or else returns null if the User is null.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "user to be saved"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public UserDTO createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = UserDtoConverter.convertDtoToEntity(userCreateDTO);
        User createdUser = userService.create(user);

        return UserDtoConverter.convertEntityToDto(createdUser);
    }

    @PutMapping("/{userId}")
    @Operation(
            tags = "User",
            operationId = "updateUser",
            summary = "update User",
            description = "Finds and updates the User by given user id. If there is no such element null will be returned",
            parameters = @Parameter(
                    name = "userId",
                    description = "id of the user that we want to update",
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "user object that will be overriding the existing one"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public UserDTO updateUser(@PathVariable int userId, @Valid @RequestBody UserDTO userDTO) {
        User user = UserDtoConverter.convertDtoToEntity(userDTO);
        User updatedUser = userService.update(userId, user);

        return UserDtoConverter.convertEntityToDto(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(
            tags = "User",
            operationId = "deleteUser",
            summary = "delete User",
            description = "Finds and soft deletes the User by given id." +
                    " If there is no such element null will be returned.",
            parameters = @Parameter(
                    name = "userId",
                    description = "id of the user that we want to soft delete",
                    example = "1"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public UserDTO deleteUser(@PathVariable int userId) {
        User deletedUser = userService.delete(userId);

        return UserDtoConverter.convertEntityToDto(deletedUser);
    }
}

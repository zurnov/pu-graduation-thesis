package com.zurnov.restaurant.controller;

import com.zurnov.restaurant.dto.role.RoleDTO;
import com.zurnov.restaurant.model.Role;
import com.zurnov.restaurant.model.enumeration.RoleEnum;
import com.zurnov.restaurant.service.RoleService;
import com.zurnov.restaurant.util.PathHelper;
import com.zurnov.restaurant.util.converter.dto.RoleDtoConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PathHelper.BASE_PATH + PathHelper.ROLE_PATH)
public class RoleController {


    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{roleId}")
    @Operation(
            tags = "Role",
            operationId = "getRoleById",
            summary = "get RoleD object by id",
            description = "Finds and returns Role that corresponds to the id of the Role" +
                    " or else returns null.",
            parameters = @Parameter(
                    name = "roleId",
                    description = "id with which we want to filter all roles",
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Role.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Role Found"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = Role.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Role not found!"
                    )

            }
    )
    public RoleDTO getRoleById(@PathVariable int roleId) {
        Role role = roleService.findById(roleId);

        return RoleDtoConverter.convertEntityToDto(role);
    }

    @GetMapping()
    @Operation(
            tags = "Role",
            operationId = "getRoleByRoleName",
            summary = "get Role object by roleName",
            description = "Finds and returns Role that corresponds to the roleEnum or else list of all Roles" +
                    " if none of them matches the roleEnum.",
            parameters = @Parameter(
                    name = "roleName",
                    description = "role enum with which we want to filter all Roles",
                    example = "ADMIN"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Role.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE),
                    description = "Role Found"
            )
    )
    public List<RoleDTO> getRoles(@RequestParam(name = "roleName", required = false) RoleEnum roleName) {
        List<Role> roles = roleService.findRoles(roleName);

        return roles.stream()
                .map(RoleDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }
}

package com.ead.authuser.controllers;

import com.ead.authuser.DTOs.InstructorDTO;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.RoleService;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionInstructor(
            @RequestBody @Valid InstructorDTO instructorDTO
    ) {
        Optional<UserModel> userModelOptional = userService.findById(instructorDTO.getUserId());
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            var userModel = userModelOptional.get();

            RoleModel roleModel = roleService.findByRoleType(RoleType.ROLE_INSTRUCTOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is Not Found."));

            userModel.setUserType(UserType.INSTRUCTOR);
            userModel.getRoles().add(roleModel);
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC-3")));

            userService.updateUser(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
}

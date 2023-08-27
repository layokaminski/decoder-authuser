package com.ead.authuser.DTOs;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class UserCourseDTO {

    private UUID userId;

    @NotNull
    private UUID courseId;
}

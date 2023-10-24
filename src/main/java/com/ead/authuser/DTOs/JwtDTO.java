package com.ead.authuser.DTOs;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtDTO {

    @NonNull
    private String token;
    private String type = "Bearer";
}

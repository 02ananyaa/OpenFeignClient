package com.example.superset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupersetLoginRequest {
	private String username;
    private String password;
    private String provider;
    private Boolean refresh = true;
}

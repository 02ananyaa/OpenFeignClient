package com.example.superset.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestTokenRequest {
	private String user;
    private List<String> resources;
    private Long rls;
}

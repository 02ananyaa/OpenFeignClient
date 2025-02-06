package com.example.superset.dto;

import lombok.Data;

@Data
public class SupersetAccessTokenResponse {
	private String access_token;
	private String token_type;
	private int expires_in;
}

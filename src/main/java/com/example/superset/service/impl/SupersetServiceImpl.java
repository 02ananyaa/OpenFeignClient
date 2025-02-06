package com.example.superset.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.superset.client.SupersetClient;
import com.example.superset.dto.GuestTokenRequest;
import com.example.superset.dto.GuestTokenResponse;
import com.example.superset.dto.SupersetAccessTokenResponse;
import com.example.superset.dto.SupersetLoginRequest;
import com.example.superset.service.SupersetService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupersetServiceImpl implements SupersetService {

	private final SupersetClient supersetClient;
	

	@Override
	public String fetchAccessToken() {
        SupersetLoginRequest loginRequest = new SupersetLoginRequest("your-username", "your-password", "db", true);
        SupersetAccessTokenResponse response = supersetClient.getAccessToken(loginRequest);
        return response.getAccess_token();
	}

	@Override
	public String fetchGuestToken(String accessToken) {
		 GuestTokenRequest request = new GuestTokenRequest("guest_user", List.of("dashboard"), 123L);
	        GuestTokenResponse response = supersetClient.getGuestToken("Bearer " + accessToken, request);
	        return response.getToken();
	}

}

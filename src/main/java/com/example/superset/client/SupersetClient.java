package com.example.superset.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.superset.dto.GuestTokenRequest;
import com.example.superset.dto.GuestTokenResponse;
import com.example.superset.dto.SupersetAccessTokenResponse;
import com.example.superset.dto.SupersetLoginRequest;

@FeignClient(name = "superset-auth-client", url = "${superset.base.url}")
public interface SupersetClient {

	@PostMapping("/api/v1/security/login")
	SupersetAccessTokenResponse getAccessToken(@RequestBody SupersetLoginRequest loginRequest);

    @PostMapping("/api/v1/security/guest_token")
	GuestTokenResponse getGuestToken(@RequestHeader("Authorization") String accessToken,
			@RequestBody GuestTokenRequest request);

}

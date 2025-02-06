package com.example.superset.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.superset.service.SupersetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/superset")
@RequiredArgsConstructor
public class SupersetController {

	 private final SupersetService supersetService;

	    @GetMapping("/access-token")
	    public ResponseEntity<String> getAccessToken() {
	        String token = supersetService.fetchAccessToken();
	        return ResponseEntity.ok(token);
	    }

	    @GetMapping("/guest-token")
	    public ResponseEntity<String> getGuestToken() {
	        String accessToken = supersetService.fetchAccessToken();
	        String guestToken = supersetService.fetchGuestToken(accessToken);
	        return ResponseEntity.ok(guestToken);
	    }
}

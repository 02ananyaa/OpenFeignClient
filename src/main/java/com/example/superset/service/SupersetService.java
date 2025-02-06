package com.example.superset.service;

public interface SupersetService {

	 String fetchAccessToken();
	 String fetchGuestToken(String accessToken);

}

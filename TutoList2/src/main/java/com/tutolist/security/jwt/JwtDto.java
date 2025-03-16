package com.tutolist.security.jwt;

public record JwtDto (
	String accessToken,
	String refreshToken
) {

}
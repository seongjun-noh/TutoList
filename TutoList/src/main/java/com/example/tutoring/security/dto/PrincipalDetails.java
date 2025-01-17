package com.example.tutoring.security.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.tutoring.dmain.user.enums.UserRole;

public class PrincipalDetails implements UserDetails {
	private String username;
	private String password;
	private UserRole role;

	public PrincipalDetails(String username, String password, UserRole role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(role.toString()));
		return auth;
	}
}

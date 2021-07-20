package com.allopathy.management.model.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.allopathy.management.security.model.User;

public class UserDetails
		extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -8497216847376321612L;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public UserDetails(User user) {
		super(user.getUsername(),
				user.getPassword() == null ? "" : user.getPassword(),
				createAuthority(user.getPrivilages()));
	}

	public UserDetails(User user, boolean enabled) {
		super(user.getUsername(),
				user.getPassword() == null ? "" : user.getPassword(), enabled,
				true, true, true, createAuthority(user.getPrivilages()));
	}

	public UserDetails(User user, boolean enabled, boolean accountNonLocked) {
		super(user.getUsername(),
				user.getPassword() == null ? "" : user.getPassword(), enabled,
				true, true, accountNonLocked,
				createAuthority(user.getPrivilages()));
	}

	private static Set<GrantedAuthority> createAuthority(List<String> privilages) {
		Set<GrantedAuthority> authority = privilages.stream()
				.map(privilage -> new SimpleGrantedAuthority(privilage))
				.collect(Collectors.toSet());
		return authority;
	}

}

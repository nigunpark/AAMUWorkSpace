package com.aamu.aamurest.jwt;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aamu.aamurest.aamuuser.AAMUUserDAO;
import com.aamu.aamurest.aamuuser.AAMUUserDTO;

public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AAMUUserDAO dao; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AAMUUserDTO user = dao.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("다음 이름을 찾을수 없습니다."+username));
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getAuthority()));
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
	
	public AAMUUserDTO authenticateByNameAndPassword(String username, String password) {
		AAMUUserDTO member = dao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("다음 이름을 찾을수 없습니다."+username));
		System.out.println("member.getAuthority() == "+member.getAuthority());
        if(!member.getPassword().equals(password)) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }

        return member;
    }
	
}

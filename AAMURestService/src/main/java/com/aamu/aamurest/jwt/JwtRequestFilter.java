package com.aamu.aamurest.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;


public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
    private JwtUserDetailsService jwtUserDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final List<String> EXCLUDE_URL = Collections.unmodifiableList(Arrays.asList("/authenticate","/users/checkid","/users/edit","/resources","/main/mainelement","/chat","/ws","/img/upload","/users/theme","/authenticate/email","/theme/insert"));

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestTokenHeader = request.getHeader("Authorization"); // Post ��İ� Put����� Authorization�� �ش��� ����
		String username = null;
		String jwtToken = null;
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);

			}
			catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
	        }
			catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
	        }
		}
		else {
			System.out.println("JWT Token does not begin with Bearer String");
		}

		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.jwtUserDetailService.loadUserByUsername(username);
			if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				request.setAttribute("username", username);
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return EXCLUDE_URL.stream().anyMatch(exclude ->request.getServletPath().startsWith(exclude));
	}



}

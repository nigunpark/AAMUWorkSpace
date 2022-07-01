package com.aamu.aamurest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.aamuuser.AAMUUserDTO;
import com.aamu.aamurest.jwt.JwtTokenUtil;
import com.aamu.aamurest.jwt.JwtUserDetailsService;

@RestController
@CrossOrigin
public class AuthenticateController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailService;
    
    @PostMapping("/authenticate")
    public Map createAuthenticationToken(@RequestBody Map authenticationRequest) throws Exception {
    	System.out.println(authenticationRequest);
        AAMUUserDTO member = userDetailService.authenticateByNameAndPassword
        		(authenticationRequest.get("username").toString(), authenticationRequest.get("password").toString());
        String token = jwtTokenUtil.generateToken(member.getUsername());
        Map map = new HashMap();
        map.put("member", member);
        map.put("token", token);
        System.out.println(map.get("token"));
        return map;
    }
    
    @GetMapping("/Hello")
    public String hello() {
    	return "asdfasdf";
    }
}

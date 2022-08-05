package com.aamu.aamurest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aamu.aamurest.aamuuser.AAMUUserDTO;
import com.aamu.aamurest.jwt.JwtTokenUtil;
import com.aamu.aamurest.jwt.JwtUserDetailsService;
import com.aamu.aamurest.util.FileUploadUtil;

@RestController
@CrossOrigin
public class AuthenticateController {

	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailService;

    @PostMapping("/authenticate")
    public Map createAuthenticationToken(@RequestBody Map authenticationRequest,HttpServletRequest req) throws Exception {
    	System.out.println(authenticationRequest);
        AAMUUserDTO member = userDetailService.authenticateByNameAndPassword
        		(authenticationRequest.get("username").toString(), authenticationRequest.get("password").toString());
        String token = jwtTokenUtil.generateToken(member.getUsername());
        Map map = new HashMap();
        map.put("member", member);
        map.put("token", token);
        map.put("userprofile",FileUploadUtil.requestOneFile(userDetailService.getUserProfile(member.getUsername()), "/resources/userUpload", req));
        System.out.println(map);
        return map;
    }
    
    @PostMapping("/authenticate/email")
    public Map createEmailToken(@RequestBody Map authenticationRequest,HttpServletRequest req) throws Exception {
    	System.out.println(authenticationRequest);
        AAMUUserDTO member = userDetailService.authenticateByEmail
        		(authenticationRequest.get("email").toString());
        String token = jwtTokenUtil.generateToken(member.getUsername());
        Map map = new HashMap();
        map.put("member", member);
        map.put("token", token);
        map.put("userprofile",FileUploadUtil.requestOneFile(userDetailService.getUserProfile(member.getUsername()), "/resources/userUpload", req));
        System.out.println(map);
        return map;
    }

    @GetMapping("/isOK")
    public String hello() {
    	return "ok";
    }
    /*
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(
    		@PathVariable(name = "imageName")String imageName,
    		HttpServletRequest request) {
    	return FileUploadUtil.requestFilePath(null, null, request);
    }*/
}

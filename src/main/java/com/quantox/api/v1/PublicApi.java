package com.quantox.api.v1;


import com.quantox.api.v1.dto.JwtResponseDto;
import com.quantox.api.v1.dto.UserDtoRequest;
import com.quantox.security.jwt.JwtProvider;
import com.quantox.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class PublicApi {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userService;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser( @RequestBody com.quantox.api.v1.dto.LoginCredentials loginCredentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCredentials.getUsername(),
                        loginCredentials.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponseDto(jwt, userDetails.getUsername()));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody UserDtoRequest userDtoRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDtoRequest));
        }catch (UsernameNotFoundException usernameNotFoundException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usernameNotFoundException.getMessage());
        }
    }
}
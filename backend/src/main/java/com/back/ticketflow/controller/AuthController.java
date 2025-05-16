package com.back.ticketflow.controller;

import com.back.ticketflow.dto.AuthRequest;
import com.back.ticketflow.dto.AuthResponse;
import com.back.ticketflow.dto.UserDTO;
import com.back.ticketflow.jwt.JwtService;
import com.back.ticketflow.model.User;
import com.back.ticketflow.repository.UserRepository;
import com.back.ticketflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userService.getUserByEmail(request.getEmail()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO data){
        if (userService.getUserByEmail(data.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso");
        }
        if(userService.saveUser(data) != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
        };
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
    }
    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO data) {
        if (userService.getUserByEmail(data.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre de usuario ya está en uso");
        }
        if(userService.saveAdminUser(data) != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
        };
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
    }

}

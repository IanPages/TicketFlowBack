package com.back.ticketflow.service;

import com.back.ticketflow.dto.UserDTO;
import com.back.ticketflow.model.Role;
import com.back.ticketflow.model.User;
import com.back.ticketflow.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(UserDTO userDTO){
        Role basicRole = roleService.getRoleByName("Basic").orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setBalance(0.0);
        user.setRole(basicRole);

        return userRepository.save(user);
    }

    public User saveAdminUser(UserDTO userDTO) {
        Role adminRole = roleService.getRoleByName("Admin").orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setBalance(0.0);
        user.setRole(adminRole);

        return userRepository.save(user);
    }

    public User updateUser(Integer id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return false; // User not found
        }
        userRepository.deleteById(id);
        return true;
    }

}

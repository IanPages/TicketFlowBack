package com.back.ticketflow.controller;

import com.back.ticketflow.model.Role;
import com.back.ticketflow.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/name/{roleName}")
    public String getRoleByName(@PathVariable String roleName) {
        return roleService.getRoleByName(roleName).toString();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer id) {
        Optional<Role> role = roleService.getRoleById(id);
        if (role.isPresent()) {
            return ResponseEntity.ok(role.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer id) {
        if (roleService.deleteRole(id)) {
            return ResponseEntity.ok("Rol eliminado con éxito");
        } else {
            return ResponseEntity.status(404).body("Rol no encontrado");
        }
    }


}

package Nency.project.Placement.Assistant.Controller;

import Nency.project.Placement.Assistant.model.Admin;
import Nency.project.Placement.Assistant.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3001","http://localhost:3002","http://localhost:3000","https://placement-assistant-system.vercel.app"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    // Get all admins
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Add new admin
    @PostMapping
    public Admin addAdmin(@RequestBody Admin admin) {
        if (admin.getRole() == null || admin.getRole().isEmpty()) {
            admin.setRole("Sub Admin"); // default role
        }
        return adminRepository.save(admin);
    }

    // Update role
    @PutMapping("/{id}")
    public Admin updateRole(@PathVariable String id, @RequestBody Admin updatedAdmin) {
        Optional<Admin> existing = adminRepository.findById(id);
        if (existing.isPresent()) {
            Admin admin = existing.get();
            admin.setRole(updatedAdmin.getRole());
            return adminRepository.save(admin);
        }
        return null;
    }

    // Delete admin
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable String id) {
        adminRepository.deleteById(id);
    }
}

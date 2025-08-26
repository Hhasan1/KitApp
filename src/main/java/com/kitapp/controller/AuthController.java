package com.kitapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kitapp.dto.RegisterRequest;
import com.kitapp.entity.User;
import com.kitapp.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        
        System.out.println("Login page accessed. Error: " + error + ", Logout: " + logout); // Debug
        
        if (error != null) {
            model.addAttribute("errorMessage", "Kullanıcı adı veya şifre hatalı!");
        }
        
        if (logout != null) {
            model.addAttribute("successMessage", "Başarıyla çıkış yaptınız.");
        }
        
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/api/auth/register")
    public String registerUser(@ModelAttribute RegisterRequest request, 
                              RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(request);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Kayıt başarılı! Şimdi giriş yapabilirsiniz.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        System.out.println("Auth principal: " + auth.getPrincipal());
        System.out.println("Auth name: " + auth.getName());

        System.out.println("Dashboard accessed by: " + username); // Debug
        
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "dashboard";
        }
        
        return "redirect:/login";
    }
}
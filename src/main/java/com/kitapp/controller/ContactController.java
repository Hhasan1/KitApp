package com.kitapp.controller;

import com.kitapp.dto.ContactRequest;
import com.kitapp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactRequest", new ContactRequest());
        return "contact";
    }

    @PostMapping("/api/contact")
    public String sendMessage(@ModelAttribute ContactRequest request,
                             RedirectAttributes redirectAttributes) {
        try {
            contactService.sendContactMessage(request);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Your message has been sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to send message. Please try again.");
        }
        return "redirect:/contact";
    }
}
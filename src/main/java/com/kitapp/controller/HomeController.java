package com.kitapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kitapp.repository.NewsRepository;
import com.kitapp.service.EventService;

@Controller
public class HomeController {

    @Autowired
    private EventService eventService;

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("upcomingEvents", eventService.getUpcomingEvents());
        model.addAttribute("news", newsRepository.findAllByOrderByPublishedAtDesc());
        return "dashboard";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }
}

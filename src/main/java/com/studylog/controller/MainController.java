package com.studylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
    @GetMapping("/main")
    public String getMainPage(Model model) {
    	log.info("@# Controller : Get Main Page");
    	
    	return "main";
    }
}

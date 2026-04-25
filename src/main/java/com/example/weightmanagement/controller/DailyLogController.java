package com.example.weightmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.weightmanagement.entity.DailyLog;
import com.example.weightmanagement.service.DailyLogService;

@Controller
public class DailyLogController {

	private final DailyLogService dailyLogService;

	public DailyLogController(DailyLogService dailyLogService) {
		this.dailyLogService = dailyLogService;
	}

	@GetMapping("/daily-log")
	public String getWeightLog(Model model) {

		var dailyLogs = dailyLogService.findAll();

		model.addAttribute("dailyLogs", dailyLogs);

		return "daily-log/list";
	}
	
	@GetMapping("/daily-log/new")
	public String showInputForm(Model model) {
		
		model.addAttribute("dailyLogForm", new DailyLog());
		
		return "daily-log/new";
	}
	
	@PostMapping("/daily-log")
	public String submitForm(DailyLog dailyLog) {
		
		dailyLogService.save(dailyLog);
		
		return "redirect:/daily-log";
	}
}
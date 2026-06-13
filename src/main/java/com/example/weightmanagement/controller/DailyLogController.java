package com.example.weightmanagement.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		
	    if (dailyLog.getLogDate() == null || dailyLog.getWeightKg() == null) {
	        return "redirect:/daily-log/new?error=logDateRequired";
	    }
		
		dailyLogService.save(dailyLog);
		
		return "redirect:/daily-log";
	}
	
	@GetMapping("/daily-log/{logDate}/edit")
	public String showEditForm(@PathVariable LocalDate logDate, Model model) {
		DailyLog dailyLog = dailyLogService.findByLogDate(logDate);
		
		model.addAttribute("dailyLogForm", dailyLog);
		
		return "daily-log/edit";
	}
	
	@PostMapping("/daily-log/{logDate}/edit")
	public String updateForm(@PathVariable("logDate") LocalDate logDate, @ModelAttribute("dailyLogForm") DailyLog dailyLogForm){
		dailyLogForm.setLogDate(logDate);
		dailyLogService.save(dailyLogForm);
		
		return "redirect:/daily-log";
	}
}
package com.example.weightmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.weightmanagement.entity.DailyLog;
import com.example.weightmanagement.repository.DailyLogRepository;

@Service
public class DailyLogService {
	private final DailyLogRepository dailyLogRepository;
	
	public DailyLogService(DailyLogRepository dailyLogRepository) {
		this.dailyLogRepository = dailyLogRepository;
	}
	
	public DailyLog save(DailyLog input) {
		Optional<DailyLog> existing = dailyLogRepository.findById(input.getLogDate());
		if (existing.isPresent()) {
			DailyLog existingDailyLog  = existing.get();
			existingDailyLog.setWeightKg(input.getWeightKg());
			existingDailyLog.setSleepHours(input.getSleepHours());
			existingDailyLog.setSnackType(input.getSnackType());
	        existingDailyLog.setDrink1(input.getDrink1());
	        existingDailyLog.setDrink2(input.getDrink2());
	        existingDailyLog.setDrink3(input.getDrink3());
	        return dailyLogRepository.save(existingDailyLog);
		}
		return dailyLogRepository.save(input);
	}
	
	public List<DailyLog> findAll() {
		return dailyLogRepository.findAll(Sort.by(Sort.Direction.DESC, "logDate"));
	}
	
	public DailyLog findByLogDate(LocalDate logDate) {
		return dailyLogRepository.findById(logDate).orElseThrow();
	}
}
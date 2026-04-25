package com.example.weightmanagement.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.weightmanagement.entity.DailyLog;

public interface DailyLogRepository extends JpaRepository<DailyLog, LocalDate> {

}
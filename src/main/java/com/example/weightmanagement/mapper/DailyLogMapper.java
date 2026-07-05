package com.example.weightmanagement.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.weightmanagement.entity.DailyLog;

@Mapper
public interface DailyLogMapper {
	List<DailyLog> findByPeriod(
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);
}
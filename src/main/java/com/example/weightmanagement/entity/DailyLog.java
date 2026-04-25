package com.example.weightmanagement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "daily_log")
public class DailyLog {

	@Id
	@Column(name = "log_date", nullable = false)
	private LocalDate logDate;
	
	@Column(name = "weight_kg", nullable = false, precision = 5, scale = 2)
	private BigDecimal weightKg;

	@Column(name = "sleep_hours", precision = 4, scale = 1)
	private BigDecimal sleepHours;

	@Column(name = "snack_type")
	private String snackType;
	
	@Column(name = "drink_1")
	private String drink1;
	
	@Column(name = "drink_2")
	private String drink2;
	
	@Column(name = "drink_3")
	private String drink3;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	public LocalDate getLogDate() {
		return logDate;
	}

	public void setLogDate(LocalDate logDate) {
		this.logDate = logDate;
	}

	public BigDecimal getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(BigDecimal weightKg) {
		this.weightKg = weightKg;
	}

	public BigDecimal getSleepHours() {
		return sleepHours;
	}

	public void setSleepHours(BigDecimal sleepHours) {
		this.sleepHours = sleepHours;
	}

	public String getSnackType() {
		return snackType;
	}

	public void setSnackType(String snackType) {
		this.snackType = snackType;
	}

	public String getDrink1() {
		return drink1;
	}

	public void setDrink1(String drink1) {
		this.drink1 = drink1;
	}

	public String getDrink2() {
		return drink2;
	}

	public void setDrink2(String drink2) {
		this.drink2 = drink2;
	}

	public String getDrink3() {
		return drink3;
	}

	public void setDrink3(String drink3) {
		this.drink3 = drink3;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}

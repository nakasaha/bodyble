package com.system.management.weight.form;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightRecordForm {
	private String userName;
	@PastOrPresent(message="現在もしくは過去の日付を入力してください。")
	private LocalDate recordDate;
	@DecimalMin("0.0")
	private Double weight;
}

package com.system.management.weight.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightRecordEntity {
	private Integer id;
	private String userName;
	private Date recordDate;
	private Double weight;
	private String period;
	/*
	 * チャート用コンストラクタ
	 */
	public WeightRecordEntity(Date recordDate, Double weight, String period) {
		super();
		this.recordDate = recordDate;
		this.weight = weight;
		this.period = period;
	}
	
}

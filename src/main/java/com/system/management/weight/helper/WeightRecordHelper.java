package com.system.management.weight.helper;

import com.system.management.weight.entity.WeightRecordEntity;
import com.system.management.weight.form.WeightRecordForm;

public class WeightRecordHelper {
	/*
	 * Entityへの変換
	 */
	public static WeightRecordEntity convertEntity(WeightRecordForm form) {
		WeightRecordEntity entity = new WeightRecordEntity();
		entity.setWeight(form.getWeight());
		entity.setUserName(form.getUserName());
		entity.setRecordDate(java.sql.Date.valueOf(form.getRecordDate()));
		
		return entity;
	}
	
	/*
	 * Formへの変換
	 */
	public static WeightRecordForm convertForm(WeightRecordEntity entity) {
		WeightRecordForm form = new WeightRecordForm();
		form.setWeight(entity.getWeight());
		form.setUserName(entity.getUserName());
		form.setRecordDate(entity.getRecordDate().toLocalDate());
		
		return form;
	}
	

}

package com.system.management.weight.helper;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.form.AccountForm;

public class AccountHelper {
	
	/*
	 * Entityへの変換
	 */
	public static AccountEntity convertEntity(AccountForm form) {
		AccountEntity entity = new AccountEntity();
		entity.setUsername(form.getUsername());
		entity.setAccountPassword(form.getAccountPassword());
		entity.setGender(form.getGender());
		entity.setBirthdate(form.getBirthdate());
		
		return entity;
	}
	
	/*
	 * Formへの変換
	 */
	
	public static AccountForm convertForm(AccountEntity entity) {
		AccountForm form = new AccountForm();
		form.setUsername(entity.getUsername());
		form.setAccountPassword(entity.getAccountPassword());
		form.setGender(entity.getGender());
		form.setBirthdate(entity.getBirthdate());
		form.setBirthYear(entity.getBirthdate().getYear());
		form.setBirthMonth(entity.getBirthdate().getMonthValue());
		form.setBirthDay(entity.getBirthdate().getDayOfMonth());			
		return form;
	}
	

}

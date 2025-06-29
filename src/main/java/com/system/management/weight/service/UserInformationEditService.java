package com.system.management.weight.service;

import java.util.List;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.entity.WeightRecordEntity;

public interface UserInformationEditService {
	
	
	List<WeightRecordEntity> findAllInformation(String username);
	
	AccountEntity selectByName(String name);
	
	void insertAccount(AccountEntity account);
	
	void insertWeight(WeightRecordEntity weightRecord);
	
	void update(AccountEntity account);
	
	void delete(Integer id);
	
}

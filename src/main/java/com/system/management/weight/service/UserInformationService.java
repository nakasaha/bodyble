package com.system.management.weight.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.entity.WeightRecordEntity;
import com.system.management.weight.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class UserInformationService implements UserInformationEditService {

	private final AccountRepository repository;

	@Override
	public List<WeightRecordEntity> findAllInformation(String username) {
		return repository.selectAll(username);
	}

	/*
	 * アカウント情報検索
	 */
	@Override
	public AccountEntity selectByName(String name) {

		return repository.selectByName(name);
	}

	/*
	 * アカウント登録
	 */
	@Override
	public void insertAccount(AccountEntity account) {
		repository.insertAccount(account);
	}

	/*
	 * 体重登録
	 */
	@Override
	public void insertWeight(WeightRecordEntity weightRecord) {
		repository.insertWeight(weightRecord);

	}
	/*
	 * アカウント情報更新
	 */

	@Override
	public void update(AccountEntity account) {
		repository.updateAccount(account);
	}

	/*
	 * 体重アップデート
	 */
	public void weightUpdate(WeightRecordEntity entity) {
		repository.updateWeight(entity);
	}

	/*
	 * すでに登録済みの日付か確認
	 */
	public WeightRecordEntity findByNameAndDate(String name,Date recordDate) {

		return repository.findByNameAndDate(name,recordDate);
	}

	@Override
	public void delete(Integer id) {
		/*
		 * 後日実装予定
		 */
	}

	public List<WeightRecordEntity> getWeightDataByPeriod(String userName, String period) {
		return repository.findWeightByPeriod(userName, period);
	}

}

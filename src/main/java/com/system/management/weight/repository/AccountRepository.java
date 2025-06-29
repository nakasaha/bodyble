package com.system.management.weight.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.entity.WeightRecordEntity;

@Repository
public class AccountRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public AccountEntity selectByName(String username) {
		String sql = "SELECT * FROM user_account WHERE username = ?";
		AccountEntity account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(AccountEntity.class),
				username);
		if (account == null) {
			return null;
		}
		return account;
	}

	public List<WeightRecordEntity> selectAll(String userName) {
		String sql = "SELECT * FROM weight_records WHERE user_account_username = ? ORDER BY record_date";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WeightRecordEntity.class), userName);
	}

	public int insertAccount(AccountEntity account) {
		String sql = "INSERT INTO user_account (username, account_password, gender, birthdate) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql,
				account.getUsername(),
				account.getAccountPassword(),
				account.getGender(),
				account.getBirthdate());
	}

	public int insertWeight(WeightRecordEntity record) {
		String sql = "INSERT INTO weight_records (user_account_username, record_date, weight) VALUES (?, ?, ?)";
		return jdbcTemplate.update(sql,
				record.getUserName(),
				record.getRecordDate(),
				record.getWeight());
	}

	public int updateAccount(AccountEntity account) {
		String sql = "UPDATE user_account SET account_password = ?, gender = ?, birthdate = ? WHERE username = ?";
		return jdbcTemplate.update(sql,
				account.getAccountPassword(),
				account.getGender(),
				account.getBirthdate(),
				account.getUsername());
	}
	
	public int updateWeight(WeightRecordEntity record) {
		String sql = "UPDATE weight_records SET weight = ? WHERE user_account_username = ? AND record_date = ?";
		return jdbcTemplate.update(sql,
				record.getWeight(),
				record.getUserName(),
				record.getRecordDate()
				);
	}
	/*
	 * ユーザー名と登録日から検索、
	 * 検索結果が0件の場合はnullを返し
	 * 2件以上の場合は例外をなげる
	 */
	public WeightRecordEntity findByNameAndDate(String username,Date recordDate) {
		String sql = "SELECT * FROM weight_records WHERE user_account_username = ? AND record_date = ?";
		List<WeightRecordEntity> entity = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WeightRecordEntity.class),
				username,recordDate);
		
		return DataAccessUtils.singleResult(entity);
	}

	/*
	 * 折れ線グラフに必要なデータを取得
	 */
	public List<WeightRecordEntity> findWeightByPeriod(String userName, String period) {
		String sql = "";

		switch (period) {
		case "WEEK":
			sql = """
					SELECT TO_CHAR(record_date, 'YYYY-MM-DD') AS period,
					       weight,
					       record_date
					FROM weight_records
					WHERE record_date >= CURRENT_DATE - INTERVAL '7 days'
					AND user_account_username = ?
					ORDER BY record_date
					""";
			break;
		case "MONTH":
			sql = """
					SELECT TO_CHAR(record_date, 'YYYY-MM-DD') AS period,
					       weight,
					       record_date
					FROM weight_records
					WHERE record_date >= CURRENT_DATE - INTERVAL '1 month'
					AND user_account_username = ?
					ORDER BY record_date
					""";
			break;
		case "YEAR":
			sql = """
					SELECT TO_CHAR(record_date, 'YYYY-MM') AS period,
					       AVG(weight) AS weight,
					       MIN(record_date) AS record_date
					FROM weight_records
					WHERE record_date >= CURRENT_DATE - INTERVAL '1 year'
					AND user_account_username = ?
					GROUP BY TO_CHAR(record_date, 'YYYY-MM')
					ORDER BY MIN(record_date)
					""";
			break;
		}

		return jdbcTemplate.query(sql,
				new Object[]{ userName },
				(rs, rowNum) -> new WeightRecordEntity(
						rs.getDate("record_date"), 
						rs.getDouble("weight") ,
						rs.getString("period")
						)
				);
	}
}
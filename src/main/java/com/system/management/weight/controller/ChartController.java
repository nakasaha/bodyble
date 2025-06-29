package com.system.management.weight.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.entity.WeightRecordEntity;
import com.system.management.weight.service.UserInformationService;

@RestController
public class ChartController {
	@Autowired
	private  UserInformationService service;
	
	@GetMapping("/chart-data")
    public List<WeightRecordEntity> getChartData(@RequestParam(name="period", required=false, defaultValue="WEEK") String period,HttpSession session) {
		AccountEntity account = (AccountEntity)session.getAttribute("account");
		if(account == null) {
			      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "セッションが切れています。再ログインしてください。");
		}
		return service.getWeightDataByPeriod(account.getUsername(),period);
    }
}

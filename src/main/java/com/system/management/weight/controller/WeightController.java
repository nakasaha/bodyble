package com.system.management.weight.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.entity.WeightRecordEntity;
import com.system.management.weight.form.WeightRecordForm;
import com.system.management.weight.helper.WeightRecordHelper;
import com.system.management.weight.service.UserInformationService;

@Controller
public class WeightController {
	
	@Autowired
	private UserInformationService service;
	
	@PostMapping("/weightConfirm")
	public String confirm(@Validated @ModelAttribute("weightRecord") WeightRecordForm form,
			BindingResult result,
			HttpSession session) {
		/*
		 * 入力値チェック
		 */
		if(result.hasErrors()) {
			return "weightInput";
		}
		/*
		 * ログインユーザー情報を取得して
		 * usernameをゲット
		 */
		AccountEntity loginUser = (AccountEntity) session.getAttribute("account");
	    if (loginUser == null) {
	        return "redirect:/login";
	    }
	    
		WeightRecordEntity entity = WeightRecordHelper.convertEntity(form);
		entity.setUserName(loginUser.getUsername());

		/*
		 * 登録日にダブりがないか確認
		 */
		if(service.findByNameAndDate(entity.getUserName(), entity.getRecordDate()) == null) {
			/*
			 * 検索結果0件の場合登録
			 */
			service.insertWeight(entity);
		}else{
			/*
			 * 検索結果1件の場合更新
			 */
			service.weightUpdate(entity);
		}

		
		
		return "redirect:/myPage";
	}

}

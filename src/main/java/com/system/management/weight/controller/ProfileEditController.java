package com.system.management.weight.controller;

import java.time.DateTimeException;
import java.time.LocalDate;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.form.AccountForm;
import com.system.management.weight.helper.AccountHelper;
import com.system.management.weight.service.UserInformationService;

@Controller
public class ProfileEditController {
	@Autowired
	private UserInformationService service;
	
	@PostMapping("/editProfile")
	public String edit(@Validated @ModelAttribute("account") AccountForm account,
			BindingResult result,
			@RequestParam(value ="crp", required = false)String currentPassword,
			HttpSession session) {
		
		/*
		 * エラーチェック
		 */
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println(e));
			return "profileEdit";
		}
		
		try {
			int year = account.getBirthYear();
            int month = account.getBirthMonth();
            int day = account.getBirthDay();
	        LocalDate birthday = LocalDate.of(year, month, day);
	        account.setBirthdate(birthday);
	    } catch (DateTimeException e) {
			result.rejectValue("birthdate", "Invalid.account.birthdate", "有効な日付を入力して下さい");
	    }
		/*
		 * 現在のパスワードチェック
		 */
		AccountEntity currentAccount = (AccountEntity) session.getAttribute("account");
		if(currentPassword != null) {
			if(!currentAccount.getAccountPassword().equals(currentPassword)) {
				result.rejectValue("accountPassword", "Match.account.accountPassword", "現在のパスワードが一致しません。");
			}
			/*
			 * 確認パスワード入力チェック
			 */
			if(!account.getAccountPassword().equals(account.getConfirmPassword())) {
				result.rejectValue("accountPassword", "Match.account.accountPassword", "パスワード(確認用)が一致しません。");
			}
		}
		/*
		 * ユーザー名は編集できない
		 */
		if(!currentAccount.getUsername().equals(account.getUsername())) {
			result.rejectValue("username", "EditNotAllowed.username", "ユーザー名の変更はできません。");
		}
		/*
		 * 2回目
		 * エラーチェック
		 */
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println(e));
			return "profileEdit";
		}
		
		/*
		 * 編集処理
		 */
		
		AccountEntity entity = AccountHelper.convertEntity(account);
		service.update(entity);
		session.setAttribute("account", entity);
		return "redirect:/resultPop";
	}
	
	@GetMapping("resultPop")
	public String resultPop(Model model,HttpSession session) {
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		model.addAttribute("account",account);
		return "editResultPop";
		
	}
}

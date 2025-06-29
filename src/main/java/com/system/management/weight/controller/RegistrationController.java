package com.system.management.weight.controller;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class RegistrationController {
	@Autowired
	private UserInformationService service;
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("account",new AccountForm());
		
		return "profileRegistration";
	}
	
	/*
	 * ユーザープロフィール入力画面より値を受け取り
	 * 次の画面へ遷移
	 * 確認ボタンを押すと、登録確認画面へ
	 * 戻るボタンを押すとログイン画面へ遷移
	 */
	@PostMapping("/confirm")
	public String confirm(@Validated @ModelAttribute("account") AccountForm account,
			BindingResult result,
			Model model,
			@RequestParam("action") String action) {
		
		if("back".equals(action)) {
			return "redirect:/login";
		}
				
		/*
		 * 入力エラーチェック
		 */
		if(result.hasErrors()) {
			return "profileRegistration";
		}
		
		/*
		 * 日付をLocalDateに変換し日付チェック
		 */
		try {
            int year = account.getBirthYear();
            int month = account.getBirthMonth();
            int day = account.getBirthDay();

            LocalDate birthday = LocalDate.of(year, month, day);
            
            account.setBirthdate(birthday);

          
        } catch (NumberFormatException e) {
            result.rejectValue("birthdate","invalid.birthdate", "生年月日の入力が正しくありません。");
        } catch (DateTimeException e) {
        	result.rejectValue("birthdate","invalid.birthdate", "存在しない日付が選択されています。");
        }
		// ユーザー名の重複チェック
		try {
		    if (service.selectByName(account.getUsername()) != null) {
		        result.rejectValue("username", "duplicate", "このユーザー名は既に使用されています");
		    }
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		/*
		 * パスワードの一致チェック
		 */
		if(!account.getAccountPassword().equals(account.getConfirmPassword())) {
			result.rejectValue("accountPassword", "Match.account.confirmPassword", "パスワードが一致しません。");
		}
		
		/*
		 * 2回目
		 * 入力エラーチェック
		 */
		if(result.hasErrors()) {
			return "profileRegistration";
		}
				
		model.addAttribute("account",account);
		return "profileComplete";
		
	}
	
	
	
	@PostMapping("/complete")
	public String complete(AccountForm form) {
		/*
		 * ユーザー情報の登録処理
		 */
		AccountEntity entity = AccountHelper.convertEntity(form);
		service.insertAccount(entity);
		return "redirect:/login";
	}
}

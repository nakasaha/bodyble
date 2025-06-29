package com.system.management.weight.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.form.AccountForm;
import com.system.management.weight.form.WeightRecordForm;
import com.system.management.weight.helper.AccountHelper;

@Controller
public class MyPageController {

	// カレンダー画面
	@GetMapping("/calendar")
	public String showCalendar(HttpSession session, Model model) {
		AccountEntity user = (AccountEntity) session.getAttribute("account");
		sessionCheck(session, user);
		model.addAttribute("user", user);
		return "calendar"; // calendar.html に遷移
	}

	// 体重入力画面
	@GetMapping("/weightInput")
	public String showWeightInput(HttpSession session, Model model) {
		model.addAttribute("weightRecord", new WeightRecordForm());
		return "weightInput"; // weightInput.html に遷移
	}

	// ユーザー情報編集画面
	@GetMapping("/editProfile")
	public String showEditProfile(HttpSession session, Model model) {
		AccountEntity account = (AccountEntity) session.getAttribute("account");
		sessionCheck(session, account);
		AccountForm form = AccountHelper.convertForm(account);
		model.addAttribute("account",form);
		
		return "profileEdit"; 
	}

	// セッション破棄
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}

	/*
	 * ログインチェック
	 */
	private String sessionCheck(HttpSession session, AccountEntity user) {
		if (user == null) {
			session.invalidate();
			return "redirect:/login"; // 未ログインならログイン画面へ
		}

		return null;
	}

}

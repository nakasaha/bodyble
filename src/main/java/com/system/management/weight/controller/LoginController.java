package com.system.management.weight.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.management.weight.entity.AccountEntity;
import com.system.management.weight.service.UserInformationService;

@Controller
public class LoginController {
	@Autowired
	private UserInformationService service;
	
	@GetMapping("/")
	public String getLogin(Model model) {
		/*
		 * ログイン画面へ遷移
		 */
		return "login";
	}
	
	/*
	 * ログイン判定
	 */
	@PostMapping("/ToMyPage")
	public String toMyPage(Model model,
			@RequestParam("username")String username,
			@RequestParam("password")String password,
			HttpSession session) {
		AccountEntity account = service.selectByName(username);
		
		
		if(account != null && password.equals(account.getAccountPassword())) {
			
			session.setAttribute("account", account);
			model.addAttribute("account",account);
			return "redirect:/myPage";
		}
		model.addAttribute("errorMessage","ユーザー名もしくはパスワードが間違っています。");
		
		return "login";
	}
	
	@GetMapping("/myPage")
    public String showMyPage(HttpSession session, Model model) {
        AccountEntity account = (AccountEntity) session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        }
        /*
         * ユーザー名を渡すためaccountをadd
         */
        model.addAttribute("account",account);
        return "myPage";
    }
}

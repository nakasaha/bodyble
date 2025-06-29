package com.system.management.weight.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountForm {
	@NotBlank(message="名前は必須です。")
	private String username;
	@NotBlank(message="パスワードは必須です。")
	@Size(min=8, message="パスワードは8文字以上でお願いします。")
	private String accountPassword;
	@NotBlank(message="パスワードは必須です。")
	@Size(min=8, message="パスワードは8文字以上でお願いします。")
	private String confirmPassword;
	@NotBlank(message="性別を選択してください。")
	private String gender;
	@NotNull(message="生年月日を入力してください。")
	private Integer birthYear;
	@NotNull(message="生年月日を入力してください。")
	private Integer birthMonth;
	@NotNull(message="生年月日を入力してください。")
	private Integer birthDay;
	
	private LocalDate birthdate;
	private Boolean isNew;
}

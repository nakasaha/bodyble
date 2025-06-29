package com.system.management.weight.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
	
	private String username;
	private String accountPassword;
	private String gender;
	private LocalDate birthdate;
	
}

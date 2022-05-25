package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class BalanceController {
	@GetMapping("/myBalance")
	public String getBalanceDetails(String input) {
		return "Here are the balance details from the DB";
	}
}

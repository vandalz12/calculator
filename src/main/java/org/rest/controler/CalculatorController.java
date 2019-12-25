package org.rest.controler;

import org.rest.service.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author restu
 *
 */
@RestController
public class CalculatorController {

	@Autowired
	private Calculator calculator;
	
	@RequestMapping("/sum")
	public String sum(@RequestParam("a") Integer a, @RequestParam("b") Integer b) {
		return String.valueOf(calculator.sum(a, b));
	}
	
}

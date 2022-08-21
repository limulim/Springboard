package com.spring.bbsController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.bbsCommand.Bcmd;
import com.spring.bbsCommand.ListCmd;

@Controller
public class BController {
	Bcmd cmd = null;

	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("---list()호출----");
		cmd = new ListCmd();
		cmd.service(model);
		return "list";
	}
		
	
}

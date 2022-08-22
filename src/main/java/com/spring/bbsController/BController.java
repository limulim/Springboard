package com.spring.bbsController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.bbsCommand.Bcmd;
import com.spring.bbsCommand.ListCmd;
import com.spring.bbsCommand.WriteCmd;
import com.spring.bbsVO.BVO;

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
		
	@RequestMapping("/wirteForm")
	public String writeForm(Model model) {
		System.out.println("---writeform()호출----");
		return "writeForm";
	}
	@RequestMapping("/writeOk")
	public String writeOk(HttpServletRequest request, Model model) {
		System.out.println("----writeOk()호출----");
		
		model.addAttribute("request", request);
		cmd = new WriteCmd();
		cmd.service(model);
		
		return "redirect:list";
	}
	
	@ModelAttribute("BVO")
	public BVO formBacking() {
		return new BVO();
		
	}
}

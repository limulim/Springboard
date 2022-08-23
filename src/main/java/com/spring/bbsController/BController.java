package com.spring.bbsController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.bbsCommand.Bcmd;
import com.spring.bbsCommand.ContentCmd;
import com.spring.bbsCommand.DeleteCmd;
import com.spring.bbsCommand.ListCmd;
import com.spring.bbsCommand.ModifyCmd;
import com.spring.bbsCommand.ReplayCmd;
import com.spring.bbsCommand.ReplyFormCmd;
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

	@RequestMapping("/contentView")
	public String contentView(HttpServlet request, Model model) {
		System.out.println("----contentView()호출----");

		model.addAttribute("request", request);
		cmd = new ContentCmd();
		cmd.service(model);
		return "contentView";

	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("modify 호출----");

		model.addAttribute("request", request);
		cmd = new ModifyCmd();
		cmd.service(model);

		return "redirect:list";
	}

	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()호출----");

		model.addAttribute("request", request);
		cmd = new DeleteCmd();
		cmd.service(model);

		return "redirect:list";
	}

	@RequestMapping("replyForm")
	public String replyForm(HttpServletRequest request, Model model) {
		System.out.println("----replyForm()호출");
		model.addAttribute("request", request);
		cmd = new ReplyFormCmd();
		cmd.service(model);
		
		return "replyForm";
	}
		
	@RequestMapping("replyOk")
	public String replyOk(HttpServletRequest request, Model model) {
		System.out.println("---- replyok() 호출 ----");
	
		model.addAttribute("request", request);
		cmd = new ReplayCmd();
		cmd.service(model);
		
		return "redirect:list";
	}
	
	@ModelAttribute("BVO")
	public BVO formBacking() {
		return new BVO();

	}
}

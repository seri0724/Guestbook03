package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Controller
public class GuestbookController {

	@Autowired
	private GuestbookDao dao;
	
	@RequestMapping(value="/list")
	public String list(Model model) {
		System.out.println("list 진입");
		List<GuestbookVo> list = dao.getList();
		model.addAttribute("list", list);
		return"list";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute GuestbookVo vo) {
		System.out.println("add 진입");
		System.out.println(vo.toString());
		dao.insert(vo);
		return"redirect:/list";
	}
	@RequestMapping(value="/deleteform", method=RequestMethod.GET)
	public String deleteform(@RequestParam("no") int no, Model model) {
		System.out.println("deleteform 진입");
		model.addAttribute("no", no);
		return"deleteform";
	}
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo, @RequestParam("no") int no) {
		System.out.println("delete 진입");
		System.out.println(vo.toString());
		GuestbookVo gvo = dao.select(vo.getNo());
		System.out.println(gvo.toString());
		if(vo.getPassword().equals(gvo.getPassword())) { //vo.getPassword() = 내가 입력한 비밀번호, gvo.getPassword() = 진짜비밀번호
			dao.delete(no);
		}else 
			System.out.println("비밀번호가 틀렸습니다.");
		return"redirect:/list";
	}
}
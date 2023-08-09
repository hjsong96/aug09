package com.hadine.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hadine.web.dto.BoardDTO;
import com.hadine.web.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board")
	public String board(Model model) {
		List<BoardDTO> list =  boardService.boardList();
		System.out.println(list);
		model.addAttribute("list", list);
		
		return "board";
	}
}

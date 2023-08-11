package com.hadine.web.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadine.web.dao.BoardDAO;
import com.hadine.web.dto.BoardDTO;
import com.hadine.web.util.Util;

@Service
public class BoardService {
	
	@Autowired
	private Util util;

	@Autowired
	private BoardDAO boardDAO;

	public List<BoardDTO> boardList() {
		return boardDAO.boardList();
	}

	public BoardDTO detail(int bno) {
		//읽음수 +1 하기
		boardDAO.readUP(bno);
		
		return boardDAO.detail(bno);
	}

	public int write(BoardDTO dto) {
		//ip
		dto.setBip(util.getIp());
		//uuid
		dto.setUuid(UUID.randomUUID().toString());
		return boardDAO.write(dto);
	}
	
}

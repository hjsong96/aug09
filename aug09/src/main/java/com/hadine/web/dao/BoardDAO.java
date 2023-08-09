package com.hadine.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.hadine.web.dto.BoardDTO;

@Repository
@Mapper
public interface BoardDAO {
	List<BoardDTO> boardList();

}

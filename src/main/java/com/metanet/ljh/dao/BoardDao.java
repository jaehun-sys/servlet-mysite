package com.metanet.ljh.dao;

import java.util.List;

import com.metanet.ljh.vo.BoardVo;

public interface BoardDao {

	public boolean insert(BoardVo vo);
	public boolean delete(int boardno);
	public boolean update(BoardVo vo);
	public List<BoardVo> getList(int page, String search, String kwd);
	public BoardVo getBoard(int boardno);
	public boolean upHit(int boardno);
	public int getTotalRow(String search, String kwd);
	
}

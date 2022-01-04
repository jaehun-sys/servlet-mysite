package mysite.dao;

import java.util.List;

import mysite.vo.BoardVo;

public interface BoardDao {

	public boolean insert(BoardVo vo);
	public boolean delete(int boardno);
	public boolean update(BoardVo vo);
	public List<BoardVo> getList();
	public BoardVo getBoard(int boardno);
	
}

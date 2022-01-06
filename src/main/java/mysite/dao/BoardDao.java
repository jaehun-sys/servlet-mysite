package mysite.dao;

import java.util.List;

import mysite.vo.BoardVo;

public interface BoardDao {

	public boolean insert(BoardVo vo);
	public boolean delete(int boardno);
	public boolean update(BoardVo vo);
	public List<BoardVo> getList(int page);
	public BoardVo getBoard(int boardno);
	public boolean upHit(int boardno);
	public int getTotalRow();
	
	public List<BoardVo> findByTitle(String title);
}

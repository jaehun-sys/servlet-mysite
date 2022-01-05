package mysite.dao;

import java.util.List;

import mysite.vo.BoardVo;

public class BoardDaoTest {
	public static void main(String[] args) {
		BoardDao dao = new BoardDaoImpl();
		
		List<BoardVo> list = dao.getList();
		
		System.out.println(list.get(0));
		System.out.println(dao.getBoard(2));
		
	}
}

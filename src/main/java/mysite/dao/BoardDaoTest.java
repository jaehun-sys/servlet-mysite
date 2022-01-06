package mysite.dao;

import java.util.List;

import mysite.vo.BoardVo;

public class BoardDaoTest {
	public static void main(String[] args) {
		BoardDao dao = new BoardDaoImpl();
		
		int page = 1;
		List<BoardVo> list = dao.getList(page);
		
		System.out.println("페이징을 통한 한 페이지의 행 사이즈: "+list.size());
		System.out.println("토탈 행 수: "+dao.getTotalRow());
		System.out.println(dao.getBoard(2));
		
	}
}

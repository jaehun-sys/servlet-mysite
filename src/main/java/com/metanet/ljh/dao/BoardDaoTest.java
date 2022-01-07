package com.metanet.ljh.dao;

import java.util.List;

import com.metanet.ljh.vo.BoardVo;

public class BoardDaoTest {
	public static void main(String[] args) {
		BoardDao dao = new BoardDaoImpl();
		
		int page = 1;
	    String search = "name";
	    String kwd = "";
		List<BoardVo> list = dao.getList(page, search, kwd);
		
		System.out.println("검색결과: "+list);
		System.out.println("페이징을 통한 한 페이지의 행 사이즈: "+list.size());
		//System.out.println("토탈 행 수: "+dao.getTotalRow());
		//System.out.println(dao.getBoard(2));
		
	}
}

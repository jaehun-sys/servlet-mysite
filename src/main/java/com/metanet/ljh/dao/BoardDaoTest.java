package com.metanet.ljh.dao;

import com.metanet.ljh.vo.BoardVo;

public class BoardDaoTest {
	public static void main(String[] args) {
		BoardDao dao = new BoardDaoImpl();
		
		int inputPage = 1;
		int inputNo = 1;		//user no
		int inputBoardNo = 1;	//board no
		String inputTitle = "입력할 쩨목";
		String inputContent = "입력할 내요옹";
		String inputModifyTitle = "수정할 쩨목";
		String inputModifyContent = "수정할 내용";
		
		//등록
		System.out.println("-등록 test-");
		BoardVo voTest = new BoardVo(inputTitle, inputContent, inputNo);
		System.out.println("inputPage: "+inputPage);
		System.out.println("inputNo: "+inputNo);
		System.out.println("inputTitle: "+inputTitle);
		System.out.println("inputContent: "+inputContent);
		//dao.insert(voTest);
		System.out.println(dao.getList(inputPage, "name", "정종욱"));
		
		System.out.println("=========================================");
		System.out.println("=========================================");
		
		//리스트
		System.out.println("-리스트 test-");
		System.out.println(dao.getList(inputPage, "name", "정종욱"));
		
		System.out.println("=========================================");
		System.out.println("=========================================");
		
		//수정
		System.out.println("-수정 test-");
		BoardVo voTest2 = new BoardVo(inputNo, inputModifyTitle, inputModifyContent);
		System.out.println("inputModifyTitle: "+inputModifyTitle);
		System.out.println("inputModifyContent: "+inputModifyContent);
		dao.update(voTest2);
		System.out.println(dao.getList(inputPage, "name", "정종욱"));
		System.out.println("=========================================");
		System.out.println("=========================================");
		
		//삭제
		System.out.println("-삭제 test-");
		dao.delete(inputBoardNo);
		System.out.println(dao.getList(inputPage, "name", "정종욱"));

		//System.out.println("토탈 행 수: "+dao.getTotalRow());
		//System.out.println(dao.getBoard(2));
		
	}
}

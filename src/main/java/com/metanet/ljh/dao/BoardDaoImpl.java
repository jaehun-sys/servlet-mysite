package com.metanet.ljh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.metanet.ljh.util.DBConnectionMgr;
import com.metanet.ljh.vo.BoardVo;
import com.metanet.ljh.vo.Paging;

public class BoardDaoImpl implements BoardDao{

	private DBConnectionMgr pool;
	
	public BoardDaoImpl() {
		try{
			pool = DBConnectionMgr.getInstance();
	    }catch(Exception e){
	    	System.out.println("Error : 커넥션 연결 실패");      
	    }
	}

	@Override
	public boolean insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		int count = 0;
		
		try {
			conn = pool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO BOARD ");
			sql.append("VALUES (SEQ_BOARD_NO.NEXTVAL, ?, ?, 0, SYSDATE, ?) ");

			pstmt = conn.prepareStatement(sql.toString());

			int index = 1;
			pstmt.setString(index++, vo.getTitle());
			pstmt.setString(index++, vo.getContent());
			pstmt.setInt(index++, vo.getUserno());
			//pstmt.setString(index++, vo.());

			count = pstmt.executeUpdate();

			System.out.println(count + "건 등록");
			
			success = true;
			
		} catch (Exception e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return success;
	}

	@Override
	public boolean delete(int boardno) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = pool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE 			");
			sql.append("FROM 	BOARD 	");
			sql.append("WHERE 	NO = ?	");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			int index = 1;
			pstmt.setInt(index++, boardno);
			
			//실행 결과 리턴. sql 문장 실행 후, 변경된 row 수 int 타입으로 리턴
			int r = pstmt.executeUpdate();
			//pstmt.executeQuery() : select
			//pstmt.executeUpdate() : insert, update, delete
			System.out.println(r + "건 삭제");
			
			success = true;
		}catch(Exception e){
			System.err.println("SQL 에러: " + e.getMessage());
		}finally{
			pool.freeConnection(conn, pstmt);
		}
		
		return success;
	}

	@Override
	public boolean update(BoardVo vo) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = pool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE 	BOARD	 	 ");
			sql.append("SET 	TITLE 	= ?, ");
			sql.append("		CONTENT = ?  ");
			sql.append("WHERE 	NO = ?		 ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			int index = 1;
			pstmt.setString(index++, vo.getTitle());
			pstmt.setString(index++, vo.getContent());
			pstmt.setInt(index++, vo.getNo());
			
			System.out.println("여기는 update, no:" + vo.getNo());
			//실행 결과 리턴. sql 문장 실행 후, 변경된 row 수 int 타입으로 리턴
			int r = pstmt.executeUpdate();
			//pstmt.executeQuery() : select
			//pstmt.executeUpdate() : insert, update, delete
			System.out.println(r + "건 수정");
			
			success = true;
		}catch(Exception e){
			System.err.println("SQL 에러: " + e.getMessage());
		}finally{
			pool.freeConnection(conn, pstmt);
		}
		
		return success;
	}
	
	@Override
	public List<BoardVo> getList(int page, String search, String kwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		
		try {
			conn = pool.getConnection();
			Paging paging = new Paging();
			int startRowNo = (page-1)*(paging.getDisplayRow()-1)+1;
			int endRowNo = page*(paging.getDisplayRow()-1)+1;
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT *											  							  ");
			sql.append("FROM 	( 											  							  ");
			sql.append("		SELECT *									  							  ");
			sql.append("		FROM	( 								  								  ");
			sql.append("				SELECT	ROWNUM										AS ROWNO  	, ");
			sql.append("						B.NO  										AS NO		, ");
			sql.append("						B.TITLE										AS TITLE	, ");
			sql.append("						B.CONTENT									AS CONTENT	, ");
			sql.append("						B.HIT										AS HIT	 	, ");
			sql.append("						TO_CHAR(B.REGDATE, 'YYYY-MM-DD HH24:MI')	AS REGDATE 	, ");
			sql.append("						B.USERNO									AS USERNO	, ");
			sql.append("						U.NAME 										AS NAME		  ");
			sql.append("				FROM	BOARD B , 												  ");
			sql.append("						USERS U   												  ");
			sql.append("				WHERE	B.USERNO = U.NO 		  								  ");
			sql.append("				AND 	NAME 									LIKE	? 		  ");
			sql.append("				AND 	TO_CHAR(REGDATE, 'YYYY-MM-DD HH24:MI') 	LIKE	? 		  ");
			sql.append("				AND 	TITLE 									LIKE	? 		  ");
			sql.append("				AND 	CONTENT 								LIKE	? 		  ");
			sql.append("				) 								  								  ");
			sql.append("		WHERE ROWNO >= ? 							  							  ");
			sql.append("		)											  							  ");
			sql.append("WHERE ROWNO <= ?									  							  ");
			
			pstmt = conn.prepareStatement(sql.toString());
			int index = 1;
			
			switch(search) {
			case "name":
				pstmt.setString(index++, "%"+kwd+"%");	//name
				pstmt.setString(index++, "%%");		//regdate
				pstmt.setString(index++, "%%");		//title
				pstmt.setString(index++, "%%");		//content
				break;
			case "regdate":
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%"+kwd+"%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				break;
			case "title":
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%"+kwd+"%");
				pstmt.setString(index++, "%%");
				break;
			case "content":
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%"+kwd+"%");
				break;
			default:
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				break;
			}
			
			pstmt.setInt(index++, startRowNo);
			pstmt.setInt(index++, endRowNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setRowno(rs.getInt("rowno"));
				vo.setNo(rs.getInt("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setHit(rs.getInt("hit"));
				vo.setRegdate(rs.getString("regdate"));
				vo.setUserno(rs.getInt("userno"));
				vo.setName(rs.getString("name"));
				
				list.add(vo);
			}
			System.out.println(list.size() + "건 조회");
			
		}catch(Exception ex) {
			System.out.println("Exception" + ex);
		}finally{
			pool.freeConnection(conn,pstmt,rs);
		}
		return list;
		
	}

	@Override
	public BoardVo getBoard(int boardno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo vo = new BoardVo();
		
		try {
			conn = pool.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT 	NO		, ");
			sql.append("		TITLE	, ");
			sql.append("		CONTENT	, ");
			sql.append("		USERNO	  ");
			sql.append("FROM	BOARD     ");
			sql.append("WHERE 	NO = ?    ");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, boardno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo.setNo(rs.getInt("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setUserno(rs.getInt("userno"));
			}
			//System.out.println(vo.toString());
			
		}catch(Exception ex) {
			System.out.println("Exception" + ex);
		}finally{
			pool.freeConnection(conn,pstmt,rs);
		}
		return vo;
	}

	@Override
	public boolean upHit(int boardno) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = pool.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE 	BOARD	 			 ");
			sql.append("SET 	HIT = HIT+1			 ");
			sql.append("WHERE 	NO = ?		 		 ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			int index = 1;
			pstmt.setInt(index++, boardno);
			
			//실행 결과 리턴. sql 문장 실행 후, 변경된 row 수 int 타입으로 리턴
			int r = pstmt.executeUpdate();
			//pstmt.executeQuery() : select
			//pstmt.executeUpdate() : insert, update, delete
			System.out.println(r + "조회수 증가");
			
			success = true;
		}catch(Exception e){
			System.err.println("SQL 에러: " + e.getMessage());
		}finally{
			pool.freeConnection(conn, pstmt);
		}
		
		return success;
	}

	@Override
	public int getTotalRow(String search, String kwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//List<BoardVo> list = new ArrayList<BoardVo>();
		int count = 0;
		
		try {
			conn = pool.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT 	/*+FULL(A) PARALLEL(A 8)*/  COUNT(*) ");
			sql.append("FROM	BOARD B	,							 ");
			sql.append("		USERS U								 ");
			sql.append("WHERE 	B.USERNO = U.NO						 ");
			sql.append("AND 	NAME	LIKE 	?				 	 ");
			sql.append("AND 	REGDATE	LIKE 	?				 	 ");
			sql.append("AND 	TITLE	LIKE 	?				 	 ");
			sql.append("AND 	CONTENT	LIKE 	?				 	 ");

			
			pstmt = conn.prepareStatement(sql.toString());

			int index = 1;
			switch(search) {
			case "name":
				pstmt.setString(index++, "%"+kwd+"%");	//name
				pstmt.setString(index++, "%%");			//regdate
				pstmt.setString(index++, "%%");			//title
				pstmt.setString(index++, "%%");			//content
				break;
			case "regdate":
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%"+kwd+"%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				break;
			case "title":
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%"+kwd+"%");
				pstmt.setString(index++, "%%");
				break;
			case "content":
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%"+kwd+"%");
				break;
			default:
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				pstmt.setString(index++, "%%");
				break;
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt("COUNT(*)");

			}
			
		}catch(Exception ex) {
			System.out.println("Exception" + ex);
		}finally{
			pool.freeConnection(conn,pstmt,rs);
		}
		return count;
	}

	

}

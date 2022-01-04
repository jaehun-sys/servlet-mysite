package mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.util.DBConnectionMgr;
import mysite.vo.BoardVo;

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
			pstmt.setInt(index++, vo.getNo());
			
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
	public List<BoardVo> getList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		try {
			conn = pool.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT	NO  									, ");
			sql.append("		TITLE									, ");
			sql.append("		CONTENT									, ");
			sql.append("		HIT			 							, ");
			sql.append("		TO_CHAR (REGDATE, 'YYYY-MM-DD') REGDATE	, ");
			sql.append("		USERNO									  ");
			sql.append("FROM	BOARD ");
			
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getInt("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setHit(rs.getInt("hit"));
				vo.setDate(rs.getString("date"));
				vo.setUserno(rs.getInt("userno"));
				
				list.add(vo);
			}
		}catch(Exception ex) {
			System.out.println("Exception" + ex);
		}finally{
			pool.freeConnection(conn,pstmt,rs);
		}
		return list;
		
	}

	@Override
	public BoardVo getBoard(int boardno) {
		// TODO Auto-generated method stub
		return null;
	}

	

}

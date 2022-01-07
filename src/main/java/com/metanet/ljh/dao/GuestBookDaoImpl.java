package com.metanet.ljh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.metanet.ljh.util.DBConnectionMgr;
import com.metanet.ljh.vo.GuestBookVo;

public class GuestBookDaoImpl implements GuestBookDao{
	
	private DBConnectionMgr pool;
	
	public GuestBookDaoImpl() {
		try{
			pool = DBConnectionMgr.getInstance();
	    }catch(Exception e){
	    	System.out.println("Error : 커넥션 연결 실패");      
	    }
	}
	public int insert(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = pool.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO GUESTBOOK(NO, NAME, PASSWORD, CONTENT, REGDATE) ");
			sql.append("VALUES	(SEQ_GUESTBOOK_NO.NEXTVAL, ?, ?, ?, SYSDATE) ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());

			count = pstmt.executeUpdate();

			System.out.println(count + "건 등록");

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
		return count;
	}

	public boolean delete(int num, String pwd) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = pool.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE 	");
			sql.append("FROM 	GUESTBOOK ");
			sql.append("WHERE 	NO = ? ");
			sql.append("AND 	PASSWORD = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());

			int index = 1;
			pstmt.setInt(index++, num);
			pstmt.setString(index++, pwd);

			count = pstmt.executeUpdate();

			System.out.println(count + "건 삭제");

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

	public List<GuestBookVo> getList() {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();

		try {
			conn = pool.getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT	NO									, ");
			sql.append("		NAME									, ");
			sql.append("		PASSWORD								, ");
			sql.append("		TO_CHAR (REGDATE, 'YYYY-MM-DD') REGDATE	, ");
			sql.append("		CONTENT		 							  ");
			sql.append("FROM	GUESTBOOK ");
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String regdate = rs.getString("regdate");
				String content = rs.getString("content");

				GuestBookVo vo = new GuestBookVo(no, name, password, content, regdate);
				list.add(vo);
			}

		} catch (Exception e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return list;
	}

}

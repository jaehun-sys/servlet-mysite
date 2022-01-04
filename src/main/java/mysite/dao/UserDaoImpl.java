package mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mysite.util.DBConnectionMgr;
import mysite.vo.UserVo;

public class UserDaoImpl implements UserDao {

	private DBConnectionMgr pool;
	
	public UserDaoImpl() {
		try{
			pool = DBConnectionMgr.getInstance();
	    }catch(Exception e){
	    	System.out.println("Error : 커넥션 연결 실패");      
	    }
	}
	
	@Override
	public int join(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = pool.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO USERS ");
			sql.append("VALUES	(SEQ_USERS_NO.NEXTVAL, ?, ?, ?, ?) ");

			pstmt = conn.prepareStatement(sql.toString());

			int index = 1;
			pstmt.setString(index++, vo.getName());
			pstmt.setString(index++, vo.getEmail());
			pstmt.setString(index++, vo.getPassword());
			pstmt.setString(index++, vo.getGender());

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

	@Override
	public ArrayList<UserVo> loginSelect(String inputEmail, String inputPassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UserVo> list = new ArrayList<>();

		try {
			conn = pool.getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT 	NO			, ");
			sql.append("		NAME		, ");
			sql.append("		EMAIL		, ");
			sql.append("		GENDER		  ");
			sql.append("FROM	USERS		  ");
			sql.append("WHERE	EMAIL = ?	  ");
			sql.append("AND 	PASSWORD = ?  ");
			pstmt = conn.prepareStatement(sql.toString());
			
			int index = 1;
			pstmt.setString(index++, inputEmail);
			pstmt.setString(index++, inputPassword);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String gender = rs.getString("gender");

				UserVo vo = new UserVo(no, name, email, gender);
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

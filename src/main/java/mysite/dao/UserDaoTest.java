package mysite.dao;

import mysite.vo.UserVo;

public class UserDaoTest {
	public static void main(String[] args) {
		UserDao dao = new UserDaoImpl();
		UserVo vo = new UserVo();
		
		vo.setEmail("수정한 이메일");
		vo.setName("수정한 이름");
		vo.setGender("male");
		vo.setPassword("123");
		vo.setNo(4);
		dao.update(vo);
		
		
	}
}

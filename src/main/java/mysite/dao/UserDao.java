package mysite.dao;

import java.util.ArrayList;

import mysite.vo.UserVo;

public interface UserDao {
	public int join(UserVo vo);
	public ArrayList<UserVo> loginSelect(String inputEmail, String inputPassword);
	public boolean update(UserVo vo);
}

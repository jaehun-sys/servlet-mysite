package com.metanet.ljh.dao;

import java.util.ArrayList;

import com.metanet.ljh.vo.UserVo;

public interface UserDao {
	public int join(UserVo vo);
	public ArrayList<UserVo> loginSelect(String inputEmail, String inputPassword);
	public boolean update(UserVo vo); 
	public int emailCheckCnt(String email);
}

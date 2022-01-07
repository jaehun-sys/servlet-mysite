package com.metanet.ljh.dao;

import java.util.List;

import com.metanet.ljh.vo.GuestBookVo;

public interface GuestBookDao {

	public int insert(GuestBookVo vo);
	public boolean delete(int num, String pwd);
	public List<GuestBookVo> getList();
	
}

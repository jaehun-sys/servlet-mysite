package com.metanet.ljh.dao;

import java.util.List;

import com.metanet.ljh.vo.GuestBookVo;

public class GuestBookDaoTest {

	public static void main(String[] args) {

		GuestBookDaoImpl dao = new GuestBookDaoImpl();
		List<GuestBookVo> list = dao.getList();
		System.out.println(list.toString());
	}

}

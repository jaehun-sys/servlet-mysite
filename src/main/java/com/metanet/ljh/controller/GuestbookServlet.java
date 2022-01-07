package com.metanet.ljh.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.metanet.ljh.dao.GuestBookDao;
import com.metanet.ljh.dao.GuestBookDaoImpl;
import com.metanet.ljh.vo.GuestBookVo;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");
		System.out.println("gb:" + actionName);
		
		if ("add".equals(actionName)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestBookDao dao = new GuestBookDaoImpl();
			GuestBookVo vo = new GuestBookVo(name, password, content);
			dao.insert(vo);

			response.sendRedirect("/mysite/gb");

		} else if ("deleteform".equals(actionName)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);

		} else if ("delete".equals(actionName)) {
			System.out.println("delete 들어왔다");
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			GuestBookVo vo = new GuestBookVo();
			vo.setNo(no);
			vo.setPassword(password);

			GuestBookDao dao = new GuestBookDaoImpl();
			dao.delete(vo.getNo(), vo.getPassword());

			response.sendRedirect("/mysite/gb");
		} else {
			GuestBookDao dao = new GuestBookDaoImpl();
			List<GuestBookVo> list = dao.getList();

			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

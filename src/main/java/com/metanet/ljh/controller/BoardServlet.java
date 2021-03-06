package com.metanet.ljh.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.metanet.ljh.dao.BoardDao;
import com.metanet.ljh.dao.BoardDaoImpl;
import com.metanet.ljh.util.WebUtil;
import com.metanet.ljh.vo.BoardVo;
import com.metanet.ljh.vo.Paging;
import com.metanet.ljh.vo.UserVo;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String actionName = request.getParameter("a");
		System.out.println("board:" + actionName);

		if ("list".equals(actionName)) {
			BoardDao dao = new BoardDaoImpl();

			//검색 키워드 받기
			String search="";
			String kwd="";
			
			if(request.getParameter("search")!=null && request.getParameter("kwd")!=null) {				
				search = request.getParameter("search");
				kwd = request.getParameter("kwd");
			}
			System.out.println("여기는 BoardServlet search: "+search);
			System.out.println("여기는 BoardServlet kwd: "+kwd);
			
			// 리스트 가져오기(페이징)
			int page = 1;
			
			if(request.getParameter("page")!=null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			Paging paging = new Paging();
			paging.setPage(page);
			paging.setTotalRow(dao.getTotalRow(search, kwd));
			paging.paging();
			
			//paging 객체 화면에 보내기
			request.setAttribute("paging",paging);
			request.setAttribute("search",search);
			request.setAttribute("kwd",kwd);
			
			List<BoardVo> list = dao.getList(page, search, kwd);
			//System.out.println(list.toString());

			// 리스트 화면에 보내기
			request.setAttribute("list", list);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("read".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);

			//System.out.println(boardVo.toString());

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
			dao.upHit(no);	//조회수 증가
		} else if ("modifyform".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
		} else if ("modify".equals(actionName)) {
			// 게시물 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println("여기는 modify, no:"+no);
			
			BoardVo vo = new BoardVo(no, title, content);
			BoardDao dao = new BoardDaoImpl();
			
			dao.update(vo);
			
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		} else if ("writeform".equals(actionName)) {
			// 로그인 여부체크
			UserVo authUser = getAuthUser(request);
			if (authUser != null) { // 로그인했으면 작성페이지로
				WebUtil.forward(request, response, "/WEB-INF/views/board/write.jsp");
			} else { // 로그인 안했으면 리스트로
				System.out.println("로그인을 해야함");
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}

		} else if ("write".equals(actionName)) {
			UserVo authUser = getAuthUser(request);

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			int userNo = authUser.getNo();
			System.out.println("userNo : ["+userNo+"]");
			System.out.println("title : ["+title+"]");
			System.out.println("content : ["+content+"]");

			BoardVo vo = new BoardVo(title, content, userNo);
			BoardDao dao = new BoardDaoImpl();
			dao.insert(vo);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		} else if ("delete".equals(actionName)) {
			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao dao = new BoardDaoImpl();
			dao.delete(no);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		} else {
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// 로그인 되어 있는 정보를 가져온다.
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

}

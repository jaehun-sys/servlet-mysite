package mysite.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mysite.dao.UserDao;
import mysite.dao.UserDaoImpl;
import mysite.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");
		System.out.println("user:" + actionName);
		
		HttpSession session = request.getSession(false);
		
		//로그인
		if("loginform".equals(actionName)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);
		}
		else if ("login".equals(actionName)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			System.out.println("입력한 email: " + email);
			System.out.println("입력한 passwd: " + password);

			UserDao dao = new UserDaoImpl();
			UserVo vo = new UserVo(email, password);
			
			if(!dao.loginSelect(email, password).isEmpty()) {
				System.out.println("로그인 success!");
				
				session = request.getSession(true);
				session.setAttribute("sessionEmail", vo.getEmail() );
				session.setAttribute("sessionPasswd", vo.getPassword() );

				ArrayList<UserVo> sessionVoList = dao.loginSelect((String)session.getAttribute("sessionEmail"), (String)session.getAttribute("sessionPasswd"));
				if(!sessionVoList.isEmpty()) {
					session.setAttribute("authUser", sessionVoList.get(0));
				}else {
					System.out.println("session 못만든다 임마");
					session = request.getSession(false);
				}
				
				response.sendRedirect("/mysite/main");				
			}else {
				System.out.println("로그인 fail!");
				response.sendRedirect("/mysite/user");
			}
		} else if ("joinform".equals(actionName)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp");
			rd.forward(request, response);
		} else if ("joinsuccess".equals(actionName)) {
			System.out.println("과연 회원가입을 성공할 것인가?");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			
			UserDao dao = new UserDaoImpl();
			dao.join(vo);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp");
			rd.forward(request, response);

		} else if("modifyform".equals(actionName)){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/modifyform.jsp");
			rd.forward(request, response);
		} else if("modify".equals(actionName)){
			
		} else if("logout".equals(actionName)){
//			System.out.println("emailKey: " + emailKey);
//			UserDao dao = new UserDaoImpl();
//			List<UserVo> list = dao.loginSelect(emailKey, (String)session.getAttribute("sessionPasswd"));
//
//
//			request.setAttribute("authUser", list.get(0).getName());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/main/index.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

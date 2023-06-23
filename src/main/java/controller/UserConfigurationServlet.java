package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import dao.AgendaDao;
/**
 * Servlet implementation class UserConfigurationServlet
 */
@WebServlet(urlPatterns= {"/userconfiguration"})
public class UserConfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AgendaDao agendaDao = new AgendaDao();
	GlobalFunctions globalFunction = new GlobalFunctions();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserConfigurationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			if(globalFunction.checkSession(request, response)){
				HttpSession session=request.getSession();
				String userid = (String) session.getAttribute("userid");
				List<User> user = agendaDao.getUser(userid);
				request.setAttribute("usuario", user);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/detailsuser.jsp");
		        dispatcher.forward(request, response);
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		        dispatcher.forward(request, response);
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			if(globalFunction.checkSession(request, response)) {
				HttpSession session=request.getSession();
				String userid = (String) session.getAttribute("userid");
				String oldpassword = request.getParameter("oldpassword");
				String newpassword = request.getParameter("newpassword");
				String confirmpassword = request.getParameter("confirmpassword");
				String preference = request.getParameter("btnradio");
				if(oldpassword.equals("") || newpassword.equals("") || confirmpassword.equals("")) {
					if(agendaDao.changePreference(preference,userid)>0) {
						session.setAttribute("change", "true");
						session.setAttribute("preference", preference);
					}else {
						session.setAttribute("change", "false");
					}
					doGet(request, response);
				}else {
					if(confirmpassword.equals(newpassword)) {
						List<User> user = agendaDao.getUser(userid);
						String userpassword = user.get(0).getPassword();
						if(PasswordEncryptor.checkPassword(oldpassword, userpassword)) {
							if(agendaDao.changePassword(userid,PasswordEncryptor.encryptPassword(newpassword),preference)>0) {
								session.setAttribute("password", "true");
							}else {
								session.setAttribute("password", "false");
							}
						}else {
							session.setAttribute("password", "false");
						}
						doGet(request, response);
					}
				}
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		        dispatcher.forward(request, response);
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}

}

package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.AgendaDao;
import model.User;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns= {"/login", "/"})
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AgendaDao agendaDao = new AgendaDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		
		try {
			if(agendaDao.validateUser(user)) {
				user = agendaDao.listUser(login);
				if(PasswordEncryptor.checkPassword(password, user.getPassword())){
					HttpSession session=request.getSession();  
				    session.setAttribute("userid", user.getId());  
				    session.setAttribute("login", user.getLogin());
				    session.setAttribute("preference", user.getPreference());
			        RequestDispatcher dispatcher = request.getRequestDispatcher("/activitiesdetails");
			        dispatcher.forward(request, response);
				}else {
					request.setAttribute("wrongPassword", "true");
			        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			        dispatcher.forward(request, response);
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

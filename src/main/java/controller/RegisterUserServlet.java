package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.AgendaDao;
import model.User;

/**
 * Servlet implementation class RegisterUserServlet
 */
@WebServlet(urlPatterns= {"/register"})
public class RegisterUserServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;
	
	AgendaDao agendaDao = new AgendaDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/newuser.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String rawpassword = request.getParameter("password");
		String confirmpassword = request.getParameter("passwordconfirm");
		if(rawpassword.equals(confirmpassword)) {
			String password = PasswordEncryptor.encryptPassword(rawpassword);
		
			User user = new User();
			user.setEmail(email);
			user.setLogin(login);
			user.setPassword(password);
		
		
			try {
				if(agendaDao.registerUser(user)>0) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
					dispatcher.forward(request, response);
				}else {
					request.setAttribute("exist", "true");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/newuser.jsp");
					dispatcher.forward(request, response);
				}
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			request.setAttribute("wrong", "true");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/newuser.jsp");
			dispatcher.forward(request, response);
		}
		
	}

    
}

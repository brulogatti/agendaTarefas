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
import dao.AgendaDao;
import model.Agenda;
import model.User;

/**
 * Servlet implementation class ActivitiesDetailsServlet
 */
@WebServlet(urlPatterns= {"/activitiesdetails"})
public class ActivitiesDetailsServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AgendaDao agendaDao = new AgendaDao();
	GlobalFunctions globalFunction = new GlobalFunctions();
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivitiesDetailsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(globalFunction.checkSession(request, response)) {
			User user = new User();
			String login = (String) request.getParameter("login");
			String password = (String) request.getParameter("password");
			HttpSession session=request.getSession(); 
			session.removeAttribute("date");
			
			//Se o campos vierem vazios da atividade, encontrar o usuário a partir da sessão
			if(login == null || password == null) {
			    String userid=(String)session.getAttribute("userid"); 
			    try {
					user = agendaDao.listUserId(userid);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}else {
				try {
					user = agendaDao.listUser(login);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			try {
				session.setAttribute("preference", user.getPreference());
				List<Agenda> agenda = agendaDao.listActivities(user.getId());
				request.setAttribute("tarefa", agenda);
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/activitiesdetails.jsp");
			     dispatcher.forward(request, response);
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
			}else {
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			     dispatcher.forward(request, response);
			}
		} catch (ClassNotFoundException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(globalFunction.checkSession(request, response)) {
			doGet(request, response);
			}else {
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			     dispatcher.forward(request, response);
			}
		} catch (ClassNotFoundException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

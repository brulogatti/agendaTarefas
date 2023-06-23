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
import model.Agenda;
import model.User;
import dao.AgendaDao;

/**
 * Servlet implementation class ActivitiesDeleteServlet
 */
@WebServlet(urlPatterns= {"/delete"})
public class ActivitiesDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AgendaDao agendaDao = new AgendaDao();
	GlobalFunctions globalFunction = new GlobalFunctions();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivitiesDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(globalFunction.checkSession(request, response)) {
			User user = new User();
			HttpSession session=request.getSession(false);  
			String userid=(String)session.getAttribute("userid"); 
			    try {
					user = agendaDao.listUserId(userid);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			
			try {
				List<Agenda> agenda = agendaDao.listActivities(user.getId());
				request.setAttribute("tarefa", agenda);
				session.setAttribute("preference", user.getPreference());
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
			String userid = request.getParameter("userid");
			String activityid = request.getParameter("activityid");
			
			try {
				agendaDao.deleteActivity(activityid,userid);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/activitiesdetails");
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

}

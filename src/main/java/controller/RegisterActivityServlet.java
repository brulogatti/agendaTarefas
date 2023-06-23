package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
 * Servlet implementation class RegisterActivityServlet
 */
@WebServlet(urlPatterns= {"/newactivity","/editActivity", "/deleteActivity"})
public class RegisterActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	AgendaDao agendaDao = new AgendaDao();
	GlobalFunctions globalFunction = new GlobalFunctions();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterActivityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(globalFunction.checkSession(request, response)) {
			String url = request.getRequestURI();
			boolean edit = false;
			boolean delete = false;
			HttpSession session=request.getSession(); 
			String userid=(String)session.getAttribute("userid"); 
			User user = agendaDao.listUserId(userid);
			session.setAttribute("preference", user.getPreference());
			if(url.contains("editActivity")) {
				
				String activityid = request.getParameter("activityid");

				try {
					List<Agenda> agenda = agendaDao.getActivity(activityid);
					request.setAttribute("tarefa", agenda);
					edit = true;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			if(url.contains("deleteActivity")) {
				String activityid = request.getParameter("activityid");
				
				try {
					List<Agenda> agenda = agendaDao.getActivity(activityid);
					request.setAttribute("tarefa", agenda);
					delete = true;
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			request.setAttribute("edit", edit);
			request.setAttribute("delete", delete);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/newactivity.jsp");
			dispatcher.forward(request, response);
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
				HttpSession session=request.getSession(false);  
				String userid=(String)session.getAttribute("userid"); 
				User user = agendaDao.listUserId(userid);
				session.setAttribute("preference", user.getPreference());
				
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				String strConclusionDate = request.getParameter("conclusionDate");
				String statusActivity = request.getParameter("status");
				Date conclusionDate = Date.valueOf(strConclusionDate);
				
				if(conclusionDate.after(Date.valueOf(LocalDate.now()))) {
				
					Agenda agenda = new Agenda();
					agenda.setTitle(title);
					agenda.setDescription(description);
					agenda.setStatus(statusActivity);
					agenda.setConclusionDate(conclusionDate);
				
				
					try {
						agendaDao.registerActivity(agenda, user);
					}catch(ClassNotFoundException e) {
						e.printStackTrace();
					}
				
					RequestDispatcher dispatcher = request.getRequestDispatcher("/activitiesdetails");
					dispatcher.forward(request, response);
				}else {
					session.setAttribute("date", "true");
					doGet(request, response);
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

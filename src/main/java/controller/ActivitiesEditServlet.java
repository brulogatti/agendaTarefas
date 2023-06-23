package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import dao.AgendaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Agenda;
import model.User;


/**
 * Servlet implementation class ActivitiesEditServlet
 */
@WebServlet(urlPatterns= {"/edit"})
public class ActivitiesEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AgendaDao agendaDao = new AgendaDao();
	GlobalFunctions globalFunction = new GlobalFunctions();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivitiesEditServlet() {
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
				HttpSession session=request.getSession(false);  
				String userid=(String)session.getAttribute("userid"); 
				String activityid = request.getParameter("activityid");
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				String strConclusionDate = request.getParameter("conclusionDate");
				String statusActivity = request.getParameter("status");
				Date conclusionDate = Date.valueOf(strConclusionDate);
				if(conclusionDate.after(agendaDao.getCreationDate(activityid))) {
				
					Agenda agenda = new Agenda();
					agenda.setTitle(title);
					agenda.setDescription(description);
					agenda.setStatus(statusActivity);
					agenda.setConclusionDate(conclusionDate);
					agenda.setId(activityid);
					
					
					try {
						agendaDao.editActivity(agenda,userid);
					}catch(ClassNotFoundException e) {
						e.printStackTrace();
					}
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/activitiesdetails");
					dispatcher.forward(request, response);
				}else {
					session.setAttribute("date", "true");
					String url = "/editActivity?userid="+ userid + "&activityid=" + activityid;
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
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

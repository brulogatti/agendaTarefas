package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import model.Agenda;
import model.User;

public class AgendaDao {
	
	  String serverName = "localhost";    //caminho do servidor do BD
	    
	    //String localhostPort = "8080"; //porta de acesso localhost
	    
	    String dataBasePort="3306"; //porta de acesso banco de dados

	    String mydatabase ="agenda";        //nome do seu banco de dados

	    String url = "jdbc:mysql://" + serverName + ":" + dataBasePort + "/"+ mydatabase;

	    String username = "root";        //nome de um usuário de seu BD

	    String password = "";
	    
	    public int registerActivity(Agenda agenda, User user) throws ClassNotFoundException{
	        String INSERT_ACTIVITY_SQL = "INSERT INTO tarefas" + 
	                "(titulo, descricao, data_criacao, data_conclusao, status, user) VALUES " +
	                "(?,?,?,?,?,?);";
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        int result = 0;
	        
	        try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	                
	                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACTIVITY_SQL)){
	                    
	                    preparedStatement.setString(1, agenda.getTitle());
	                    preparedStatement.setString(2, agenda.getDescription());;
	                    preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
	                    preparedStatement.setDate(4, agenda.getConclusionDate());
	                    preparedStatement.setString(5, agenda.getStatus());
	                    preparedStatement.setString(6, user.getId());
	                    
	                    System.out.println(preparedStatement);
	                    
	                    if(existUser(user.getId())) {
	                    result = preparedStatement.executeUpdate();		
	                    }
	                    
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
	                
	                
	       return result;    
	    }
	    
	    public int registerUser(User user) throws ClassNotFoundException{
            int result = 0;
	    	
	    	if(!validateUser(user)) {
	        
	         String INSERT_ACTIVITY_SQL = "INSERT INTO usuarios" + 
	                    "(login, email, password) VALUES " +
	                    "(?,?,?);";
	            
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            
	            
	            try (Connection connection = DriverManager
	                    .getConnection(url, username, password);
	                    
	                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACTIVITY_SQL)){
	                        
	                        preparedStatement.setString(1, user.getLogin());
	                        preparedStatement.setString(2, user.getEmail());;
	                        preparedStatement.setString(3,  user.getPassword());
	                        
	                        System.out.println(preparedStatement);
	                        
	                        result = preparedStatement.executeUpdate();		
	                        
	            }catch(SQLException e) {
	                e.printStackTrace();
	            }
	    	}          
	                    
	           return result;
	    	 
	        
	    }
	    
	    public boolean validateUser(User user) throws ClassNotFoundException {
	        boolean status = false;

	        Class.forName("com.mysql.cj.jdbc.Driver");

	        try (Connection connection = DriverManager
	            .getConnection(url, username, password);

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection
	            .prepareStatement("select login from usuarios where login = ?")) {
	            preparedStatement.setString(1, user.getLogin());

	            System.out.println(preparedStatement);
	            ResultSet rs = preparedStatement.executeQuery();
	            status = rs.next();

	        } catch (SQLException e) {
	            // process sql exception
	            e.printStackTrace();
	        }
	        return status;
	    }
	    
	    public List<Agenda> listActivities(String userId) throws ClassNotFoundException {
	        List<Agenda> agenda = new ArrayList<Agenda>();
	        
	        String SEARCH_USER_SQL = "SELECT id, titulo, descricao, data_criacao, data_conclusao, status, user FROM tarefas WHERE user = ?";
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        try(Connection connection = DriverManager
	                .getConnection(url,username,password);
	                
	                PreparedStatement preparedStatement = connection
	                        .prepareStatement(SEARCH_USER_SQL)){
	            
	            preparedStatement.setString(1, userId);
	            
	            System.out.println(preparedStatement);
	            ResultSet rs = preparedStatement.executeQuery();

	            while(rs.next()) {
	                Agenda tarefa = mapRow(rs);
	                agenda.add(tarefa);
	                }
	            
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return agenda;
	        
	    }

	    private Agenda mapRow(ResultSet rs) throws SQLException {
			Agenda agenda = new Agenda();
			agenda.setId(rs.getString("id"));
			agenda.setTitle(rs.getString("titulo"));
			agenda.setDescription(rs.getString("descricao"));
			agenda.setConclusionDate(rs.getDate("data_conclusao"));
			agenda.setCreationDate(rs.getDate("data_criacao"));
			agenda.setStatus(rs.getString("status"));
			return agenda;
		}
	    
	    private User mapRowUser(ResultSet rs) throws SQLException{
	    	User user = new User();
	    	user.setId(rs.getString("id"));
	    	user.setLogin(rs.getString("login"));
	    	user.setEmail(rs.getString("email"));
	    	user.setPassword(rs.getString("password"));
	    	user.setPreference(rs.getString("preference"));
	    	return user;
	    }

		public User listUser(String login) throws ClassNotFoundException {
	        User user = new User();
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");

	            try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	            		
	            		

	                // Step 2:Create a statement using connection object
	                PreparedStatement preparedStatement = connection
	                .prepareStatement("select id, login, email, password, preference from usuarios where login = ?")) {
	                preparedStatement.setString(1, login);

	                System.out.println(preparedStatement);
	                ResultSet rs = preparedStatement.executeQuery();
	                
	                while(rs.next()) {
	                    user.setId(rs.getString("id"));
	                    user.setEmail(rs.getString("email"));
	                    user.setLogin(rs.getString("login"));
	                    user.setPassword(rs.getString("password"));
	                    user.setPreference(rs.getString("preference"));
	                    
	                    }
	                
	    
	                
	    }catch(SQLException e) {
	        e.printStackTrace();
	    }
	            return user;
	            }
	    
	    public User listUserId(String id) throws ClassNotFoundException {
	        User user = new User();
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");

	            try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	            		
	            		

	                // Step 2:Create a statement using connection object
	                PreparedStatement preparedStatement = connection
	                .prepareStatement("select id, login, email, password, preference from usuarios where id = ?")) {
	                preparedStatement.setString(1, id);

	                System.out.println(preparedStatement);
	                ResultSet rs = preparedStatement.executeQuery();
	                
	                while(rs.next()) {
	                    user.setId(rs.getString("id"));
	                    user.setEmail(rs.getString("email"));
	                    user.setLogin(rs.getString("login"));
	                    user.setPassword(rs.getString("password"));
	                    user.setPreference(rs.getString("preference"));
	                    }
	                
	    
	    }catch(SQLException e) {
	        e.printStackTrace();
	    }
	            return user;
	    }
	    
	    public List<Agenda> getActivity(String id) throws ClassNotFoundException{
	    	List<Agenda> agenda = new ArrayList<Agenda>();
	    	
	    	Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager
                .getConnection(url, username, password);
            		
            		

                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection
                .prepareStatement("select id, titulo, descricao, data_criacao, data_conclusao, status, user from tarefas where id = ?")) {
                preparedStatement.setString(1, id);

                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                
                while(rs.next()) {
	                Agenda tarefa = mapRow(rs);
	                agenda.add(tarefa);
	                }
                
    
            	}catch(SQLException e) {
            		e.printStackTrace();
            	}
	    	
	    	return agenda;
	    }

		public int editActivity(Agenda agenda, String userid) throws ClassNotFoundException {
			String EDIT_ACTIVITY_SQL = "UPDATE tarefas " +
					"SET titulo = ?, descricao = ?, data_conclusao=?, status=? " +
					"WHERE id=? AND user=?";
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        int result = 0;
	        
	        try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	                
	                PreparedStatement preparedStatement = connection.prepareStatement(EDIT_ACTIVITY_SQL)){
	                    
	                    preparedStatement.setString(1, agenda.getTitle());
	                    preparedStatement.setString(2, agenda.getDescription());;
	                    preparedStatement.setDate(3, agenda.getConclusionDate());
	                    preparedStatement.setString(4, agenda.getStatus());
	                    preparedStatement.setString(5, agenda.getId());
	                    preparedStatement.setString(6, userid);
	                    
	                    System.out.println(preparedStatement);
	                    
	                    if(existUser(userid)) {
	                    result = preparedStatement.executeUpdate();	
	                    }
	                    
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
	                
	       return result;    
			
		}
		
		 public boolean existUser(String userid) throws ClassNotFoundException {
		        boolean status = false;

		        Class.forName("com.mysql.cj.jdbc.Driver");

		        try (Connection connection = DriverManager
		            .getConnection(url, username, password);

		            // Step 2:Create a statement using connection object
		            PreparedStatement preparedStatement = connection
		            .prepareStatement("select id from usuarios where id = ? ")) {
		            preparedStatement.setString(1, userid);

		            System.out.println(preparedStatement);
		            ResultSet rs = preparedStatement.executeQuery();
		            status = rs.next();

		        } catch (SQLException e) {
		            // process sql exception
		            e.printStackTrace();
		        }
		        return status;
		    }

		public int deleteActivity(String activityid, String userid) throws ClassNotFoundException {
			// TODO Auto-generated method stub
			String DELETE_ACTIVITY_SQL= "DELETE FROM tarefas WHERE id = ? AND user = ?";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		        
		    int result = 0;
		    
		    try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	                
	                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACTIVITY_SQL)){
	                    
	                    preparedStatement.setString(1, activityid);
	                    preparedStatement.setString(2, userid);;
	                    
	                    System.out.println(preparedStatement);
	                    
	                    if(existUser(userid)) {
	                    result = preparedStatement.executeUpdate();	
	                    }
	                    
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
		    
		    return result;
			
		}

		public List<User> getUser(String userid) throws ClassNotFoundException{
			List<User> user = new ArrayList<User>();
			String GET_USER_SQL = "SELECT id, login, email, password, preference FROM usuarios WHERE id=?";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	            		
	                // Step 2:Create a statement using connection object
	                PreparedStatement preparedStatement = connection
	                .prepareStatement(GET_USER_SQL)) {
	                preparedStatement.setString(1, userid);

	                System.out.println(preparedStatement);
	                ResultSet rs = preparedStatement.executeQuery();
	                
	                while(rs.next()) {
		                User usuario = mapRowUser(rs);
		                user.add(usuario);
		                }
	                
	    
	            	}catch(SQLException e) {
	            		e.printStackTrace();
	            	}
			
			return user;
			
		}

		public int changePassword(String userid, String encryptPassword, String preference) throws ClassNotFoundException {
			String CHANGE_PASSWORD_SQL = "UPDATE usuarios " +
					"SET password = ?, preference = ? " +
					"WHERE id=?";
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        int result = 0;
	        
	        try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	                
	                PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD_SQL)){
	                    
	                    preparedStatement.setString(1, encryptPassword);
	                    preparedStatement.setString(2, preference);
	                    preparedStatement.setString(3, userid);
	                    
	                    
	                    System.out.println(preparedStatement);
	                    
	                    if(existUser(userid)) {
	                    result = preparedStatement.executeUpdate();	
	                    }
	                    
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
	                
	       return result;    
			
		}

		public int changePreference(String preference, String userid) throws ClassNotFoundException{
			String CHANGE_PREFERENCE_SQL = "UPDATE usuarios " +
					"SET preference = ? " +
					"WHERE id=?";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        int result = 0;
	        
	        try (Connection connection = DriverManager
	                .getConnection(url, username, password);
	                
	                PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PREFERENCE_SQL)){
	                    
	                    preparedStatement.setString(1, preference);
	                    preparedStatement.setString(2, userid);;
	                    
	                    
	                    System.out.println(preparedStatement);
	                    
	                    if(existUser(userid)) {
	                    result = preparedStatement.executeUpdate();	
	                    }
	                    
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
	                
	       return result;    
			
		}

		public List<Agenda> searchActivities(String id, String search) throws ClassNotFoundException {
			 List<Agenda> agenda = new ArrayList<Agenda>();
		        
		        String SEARCH_USER_SQL = "SELECT id, titulo, descricao, data_criacao, data_conclusao, status, user FROM tarefas WHERE user = ? AND ((descricao LIKE '%"+ search + "%') OR (titulo LIKE '%"+ search +"%'))";
		        
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        
		        try(Connection connection = DriverManager
		                .getConnection(url,username,password);
		                
		                PreparedStatement preparedStatement = connection
		                        .prepareStatement(SEARCH_USER_SQL)){
		            
		            preparedStatement.setString(1, id);
		            
		            System.out.println(preparedStatement);
		            ResultSet rs = preparedStatement.executeQuery();

		            while(rs.next()) {
		                Agenda tarefa = mapRow(rs);
		                agenda.add(tarefa);
		                }
		            
		        }catch(SQLException e) {
		            e.printStackTrace();
		        }
		        
		        return agenda;
		}

		public java.util.Date getCreationDate(String activityid) throws ClassNotFoundException {
            java.util.Date date = null;
			String SEARCH_DATE_SQL = "SELECT id, data_criacao FROM tarefas WHERE id = ? ";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        try(Connection connection = DriverManager
	                .getConnection(url,username,password);
	                
	                PreparedStatement preparedStatement = connection
	                        .prepareStatement(SEARCH_DATE_SQL)){
	            
	            preparedStatement.setString(1, activityid);
	            
	            System.out.println(preparedStatement);
	            ResultSet rs = preparedStatement.executeQuery();
	            
				while(rs.next()) {
                    date = rs.getDate("data_criacao");
                    }
	        }catch(SQLException e) {
	            e.printStackTrace();
	        }
			
			return date;
		}

}

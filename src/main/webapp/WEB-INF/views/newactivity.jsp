<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<% String userid = (String) session.getAttribute("userid"); %>
<% String date = (String) session.getAttribute("date"); %>
<% String preference = (String)session.getAttribute("preference"); 
String theme = new String();
String font = "light";
if(preference.equals("0")){
	theme="secondary";
	font="dark";
}
if(preference.equals("1")){
	theme="primary";
	font="dark";
}
if(preference.equals("2")){
	theme="success";
	
	font="dark";
}
if(preference.equals("3")){
	theme="danger";
	font="dark";
}
if(preference.equals("4")){
	theme="warning";
}
if(preference.equals("5")){
	theme="info";
}
if(preference.equals("6")){
	theme="light";
}
if(preference.equals("7")){
	theme="dark";
	font="dark";
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Registrar Nova Atividade</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<style>
    html,
    body {
    height: 100%;
    }

    .form-register-activity {
    max-width: 330px;
    padding: 1rem;
    }

    .form-register-activity .form-floating:focus-within {
    z-index: 2;
    }

</style>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-<%=font%> bg-<%=theme%>">
        <div class="container-fluid">
          <a class="navbar-brand" href="./activitiesdetails">Agenda</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Opções
                </a>
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" href="./activitiesdetails">Ver tarefas</a></li>
                  <li><a class="dropdown-item" href="./newactivity">Criar tarefa</a></li>
                </ul>
              </li>
              <li class="nav-item">
                <a href="./userconfiguration" class="nav-link">Configurações</a>
                </li>
              <li class="nav-item">
                 <a href = "./logout" class="nav-link">Logout</a>
              </li>
            </ul>
          </div>
        </div>
    </nav>

    <main class="form-register-activity w-100 m-auto">
    <c:if test="${requestScope.edit == true}">
    <form action="<%=request.getContextPath() %>/edit" method="post">  
        <fieldset>
            <legend>Editar atividade</legend>
            <c:forEach items = "${requestScope.tarefa}" var="c">
            <input type="text" class="form-control" value="${c.id}" placeholder="Id" aria-label="Id" name="activityid" hidden>
            <p>
                <label for="title">Título:</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" value="${c.title}" placeholder="Título" aria-label="Título" aria-describedby="basic-addon1" name="title" required>
                </div>
            </p>
            <p>
                <label for="description">Descrição:</label>
                <div class="input-group mb-3">
                    <input type="text" value="${c.description}" class="form-control" placeholder="Descrição" aria-label="Descrição" aria-describedby="basic-addon1" name="description" required>
                </div>
            </p>
            <p>
                <label for="creationDate">Data Criação:</label>
                <div class="input-group mb-3">
                    <input type="date" value="${c.creationDate}" class="form-control" name="creationDate" disabled>
                </div>
            </p>
            <p>
                <label for="conclusionDate">Data Conclusão:</label>
                <div>
                    <input type="date" value="${c.conclusionDate}" class="form-control" name="conclusionDate" required>
                </div>
            </p>
            <p>
                <label for="status">Status:</label>
                <select class="form-select" name="status" >
                	<c:if test="${c.status == 0}">
                    <option value="0" selected>Em aberto</option>
                    <option value="1">Em andamento</option>
                    <option value="2">Concluída</option>
                    </c:if>
                    <c:if test="${c.status == 1}">
                    <option value="0">Em aberto</option>
                    <option value="1" selected>Em andamento</option>
                    <option value="2">Concluída</option>
                    </c:if>
                    <c:if test="${c.status == 2}">
                    <option value="0">Em aberto</option>
                    <option value="1">Em andamento</option>
                    <option value="2" selected>Concluída</option>
                    </c:if>
                </select>
            </p>
            <p>
                <div class="input-group mb-3">
                    <input type="submit" value="Editar" class="btn btn-outline-<%=theme%>" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Editar">
                    <a href="./activitiesdetails" class="btn btn-outline-<%=theme%>" aria-label="Voltar">Voltar</a>
                </div>
            
            </p>
        </c:forEach>
        </c:if>
         <c:if test="${requestScope.delete == true}">
         <c:forEach items = "${requestScope.tarefa}" var="c">
    <form action="<%=request.getContextPath() %>/delete?userid=<%=userid%>&activityid=${c.id}" method="post">  
        <fieldset>
            <legend>Apagar atividade</legend>
             <p>
                <label for="title">Id:</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" value="${c.id}" placeholder="Id" aria-label="Id" aria-describedby="basic-addon1" name="id" disabled>
                </div>
             </p>
             <p>
                <label for="title">Título:</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" value="${c.title}" placeholder="Título" aria-label="Título" aria-describedby="basic-addon1" name="title" disabled>
                </div>
            </p>
            <p>
                <label for="description">Descrição:</label>
                <div class="input-group mb-3">
                    <input type="text" value="${c.description}" class="form-control" placeholder="Descrição" aria-label="Descrição" aria-describedby="basic-addon1" name="description" disabled>
                </div>
            </p>
            <p>
                <label for="creationDate">Data Criação:</label>
                <div class="input-group mb-3">
                    <input type="date" value="${c.creationDate}" class="form-control" name="creationDate" disabled>
                </div>
            </p>
            <p>
                <label for="conclusionDate">Data Conclusão:</label>
                <div>
                    <input type="date" value="${c.conclusionDate}" class="form-control" name="conclusionDate" disabled>
                </div>
            </p>
            <p>
                <label for="status">Status:</label>
                <select class="form-select" name="status" disabled>
                    <c:if test="${c.status == 0}">
                    <option value="0" selected>Em aberto</option>
                    <option value="1">Em andamento</option>
                    <option value="2">Concluída</option>
                    </c:if>
                    <c:if test="${c.status == 1}">
                    <option value="0">Em aberto</option>
                    <option value="1" selected>Em andamento</option>
                    <option value="2">Concluída</option>
                    </c:if>
                    <c:if test="${c.status == 2}">
                    <option value="0">Em aberto</option>
                    <option value="1">Em andamento</option>
                    <option value="2" selected>Concluída</option>
                    </c:if>
                </select>
            </p>
            <p>
                <div class="input-group mb-3">
                    <input type="submit" value="Apagar" class="btn btn-outline-<%=theme%>" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Apagar">
                     <a href="./activitiesdetails" class="btn btn-outline-<%=theme%>" aria-label="Voltar">Voltar</a>
                </div>
            
            </p>
        </c:forEach>
        </c:if>
        <c:if test="${requestScope.edit == false && requestScope.delete==false}">
        <% 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        String now = dtf.format(LocalDateTime.now());  
        %>
        <form action="<%=request.getContextPath() %>/newactivity" method="post">  
        <fieldset>
            <legend>Registrar Nova atividade</legend>
            <p>
                <label for="title">Título:</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Título" aria-label="Título" aria-describedby="basic-addon1" name="title" required>
                </div>
            </p>
            <p>
                <label for="description">Descrição:</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Descrição" aria-label="Descrição" aria-describedby="basic-addon1" name="description" required>
                </div>
            </p>
            <p>
                <label for="creationDate">Data Criação:</label>
                <div class="input-group mb-3">
                    <input type="date" value="<%=now%>" class="form-control" name="creationDate" disabled>
                </div>
            </p>
            <p>
                <label for="conclusionDate">Data Conclusão:</label>
                <div>
                    <input type="date" class="form-control" name="conclusionDate" required>
                </div>
            </p>
            <p>
                <label for="status">Status:</label>
                <select class="form-select" name="status" >
                    <option value="0" selected>Em aberto</option>
                    <option value="1">Em andamento</option>
                    <option value="2">Concluída</option>
                </select>
            </p>
            <p>
                <div class="input-group mb-3">
                    <input type="submit" value="Cadastrar" class="btn btn-outline-<%=theme%>" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Enviar">
                	<a href="./activitiesdetails" class="btn btn-outline-<%=theme%>" aria-label="Voltar">Voltar</a>
                </div>
            </p>
        </c:if>

        </fieldset>
    </form>
    </main>
    
    <div class="alert-user w-100 m-auto" style="max-width: 330px;">
	    <div class="alert alert-danger alert-dismissible fade show" role="alert" id="alert-user-div" hidden>
	  		<strong>Erro!</strong> Data de criação posterior a data de conclusão.
	  		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

    <script>
        let element = document.getElementById("alert-user-div");
        let hidden = '<%= date %>';
        console.log(hidden);
    
        if (hidden == "null") {
          element.setAttribute("hidden", "hidden");
  
        } else {
          element.removeAttribute("hidden");
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>


</body>
</html>
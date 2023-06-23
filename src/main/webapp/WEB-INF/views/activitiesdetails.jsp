<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<% String login = (String) session.getAttribute("login"); %>
<% String userid = (String) session.getAttribute("userid"); %>
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
<title>Tarefas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<style>
    html,
    body {
    height: 100%;
    }

    .activities-details {
    max-width: 80%;
    padding: 1rem;
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
            <form class="d-flex" role="search" method="post" action="<%=request.getContextPath() %>/search">
              <input class="form-control me-2" type="search" placeholder="Buscar" aria-label="Buscar" name="search">
              <input class="btn btn-success" type="submit" value="Buscar">
            </form>
          </div>
        </div>
    </nav>

    <main class="activities-details w-100 m-auto">

    <h1>Tarefas de <%=login%></h1>

    <table class="table table-<%=theme%> table-striped">
        <thead>
        	<tr>
            <th>Id</th>
            <th>Título</th>
            <th>Descrição</th>
            <th>Data Criação</th>
            <th>Data Conclusão</th>
            <th>Status</th>
            <th>Ações</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items = "${requestScope.tarefa}" var="c">
            <tr>
                <td>${c.id}</td>
                <td>${c.title}</td>
                <td>${c.description}</td>
                <td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${c.creationDate}" /></td>
                <td><fmt:formatDate pattern = "dd/MM/yyyy" value = "${c.conclusionDate}" /></td>
                <td>
                <c:if test="${c.status==0}">Em aberto</c:if>
                <c:if test="${c.status==1}">Em andamento</c:if>
                <c:if test="${c.status==2}">Concluída</c:if>
                </td>
                <td><a class="btn btn-outline-<%=theme%>" role="button" href="./editActivity?userid=<%=userid%>&activityid=${c.id}">Editar</a>   <a class="btn btn-outline-<%=theme%>" role="button" href="./deleteActivity?userid=<%=userid%>&activityid=${c.id}">Apagar</a></td>
            </tr>
            </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-primary" role="button" href="./newactivity">Cadastrar nova tarefa</a>

    <nav aria-label="pagination-model">
      <ul class="pagination justify-content-center">
        <li class="page-item disabled">
          <a class="page-link">Previous</a>
        </li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item">
          <a class="page-link" href="#">Next</a>
        </li>
      </ul>
    </nav>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </main>

</body>
</html>
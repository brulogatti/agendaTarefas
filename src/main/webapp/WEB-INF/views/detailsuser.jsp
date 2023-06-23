<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<% String login=(String) session.getAttribute("login"); %>
<% String userid=(String) session.getAttribute("userid"); %>
<% String change = (String) session.getAttribute("change"); %>
<% String password = (String)session.getAttribute("password"); %>
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
    <title>Configurações Usuário</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <style>
        body {
            color: #4d5154;;
        }

        .avatar-xl img {
            width: 110px;
        }

        .rounded-circle {
            border-radius: 50% !important;
        }

        img {
            vertical-align: middle;
            border-style: none;
        }

        .text-muted {
            color: #aeb0b4 !important;
        }

        .text-muted {
            font-weight: 300;
        }

        .form-control {
            display: block;
            width: 100%;
            height: calc(1.5em + 0.75rem + 2px);
            padding: 0.375rem 0.75rem;
            font-size: 0.875rem;
            font-weight: 400;
            line-height: 1.5;
            color: #4d5154;
            background-color: #ffffff;
            background-clip: padding-box;
            border: 1px solid #8a8a8a;;
            border-radius: 0.25rem;
            transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
        }
    </style>
    
</head>

<body>

    <nav class="navbar navbar-expand-lg navbar-<%=font%> bg-<%=theme%>">
        <div class="container-fluid">
            <a class="navbar-brand" href="./activitiesdetails">Agenda</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            Opções
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="./activitiesdetails">Ver
                                    tarefas</a></li>
                            <li><a class="dropdown-item" href="./newactivity">Criar tarefa</a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a href="./userconfiguration" class="nav-link">Configurações</a>
                    </li>
                    <li class="nav-item">
                        <a href="./logout" class="nav-link">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <main>
        <div class="danger-user w-100 m-auto">
	    <div class="alert alert-danger alert-dismissible fade show" role="alert" id="danger-user-div"hidden>
	  		<strong></strong> 
	  		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		</div>
		<div class="success-user w-100 m-auto">
	    <div class="alert alert alert-success alert-dismissible fade show" role="alert" id="success-user-div" hidden>
	  		<strong></strong> 
	  		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		</div>
		
		<script>

        let elementDanger = document.getElementById("danger-user-div");
        let elementSuccess = document.getElementById("success-user-div");
        let change = '<%= change %>';
        let password = '<%= password%>';
        console.log(change);
    
        if (change == "null" && password=="null") {
          elementDanger.setAttribute("hidden", "hidden");
          elementSuccess.setAttribute("hidden", "hidden");
        } else {
        	if(change=="true"||password=="true"){
                elementSuccess.removeAttribute("hidden");
                elementDanger.setAttribute("hidden", "hidden");
                if(change=="true" && (password=="false"||password=="null")){
                	elementSuccess.textContent="Preferências alteradas com sucesso!";
                }else{
                	elementSuccess.textContent="Senha alterada com sucesso!";
                }
        	}else{
        		if(change=="false"||password=="false"){
                	elementDanger.removeAttribute("hidden");
                	elementSuccess.setAttribute("hidden", "hidden");
                	if(change=="false" && (password=="false"||password=="null")){
                		elementDanger.textContent="Preferências não foram alteradas!"
                	}else{
                		elementDanger.textContent="Senha não foi alterada!"
                	}
        		}else{
        	          elementDanger.setAttribute("hidden", "hidden");
        	          elementSuccess.setAttribute("hidden", "hidden");
        		}
        	}
        }
    	</script>
    	
        <c:forEach items = "${requestScope.usuario}" var="c">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-10 col-xl-8 mx-auto">
                    <h2 class="h3 mb-4 page-title">Configurações</h2>
                    <div class="my-4">
                        <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="home-tab" data-toggle="tab" href="./userconfiguration" role="tab"
                                    aria-controls="home" aria-selected="false">Perfil</a>
                            </li>
                        </ul>
                        <form method="post" action="<%=request.getContextPath() %>/userconfiguration">
                            <div class="row mt-5 align-items-center">
                                <div class="col-md-3 text-center mb-5">
                                    <div class="avatar avatar-xl">
                                        <img src="https://cdn-icons-png.flaticon.com/512/17/17004.png?w=740&t=st=1686740836~exp=1686741436~hmac=2a20782ec477a9c4cc8486085526fe9d90c67cb53799ae86c5ee71d95707ec81" alt="..."
                                            class="avatar-img rounded-circle" />
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="row align-items-center">
                                        <div class="col-md-7">
                                            <h4 class="mb-1">${c.login}</h4>
                                        </div>
                                    </div>
                                    <div class="row mb-4">
                                        <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                                            <c:if test="${c.preference == 1}">
                                            <input type="radio" class="btn-check btn-primary" name="btnradio" value="1" id="btnradio1" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 1}">
                                            <input type="radio" class="btn-check btn-primary" name="btnradio" value="1" id="btnradio1" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-primary" for="btnradio1">Primary</label>
                                          
                                            <c:if test="${c.preference == 0}">
                                            <input type="radio" class="btn-check" name="btnradio" value="0" id="btnradio2" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 0}">
                                            <input type="radio" class="btn-check" name="btnradio" value="0" id="btnradio2" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-secondary" for="btnradio2">Secondary</label>
                                          
                                            <c:if test="${c.preference == 2}">
                                            <input type="radio" class="btn-check" name="btnradio" value="2" id="btnradio3" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 2}">
                                            <input type="radio" class="btn-check" name="btnradio" value="2" id="btnradio3" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-success" for="btnradio3">Success</label>

                                            <c:if test="${c.preference == 3}">
                                            <input type="radio" class="btn-check" name="btnradio" value="3" id="btnradio4" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 3}">
                                            <input type="radio" class="btn-check" name="btnradio" value="3" id="btnradio4" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-danger" for="btnradio4">Danger</label>

                                            <c:if test="${c.preference == 4}">
                                            <input type="radio" class="btn-check" name="btnradio" value="4" id="btnradio5" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 4}">
                                            <input type="radio" class="btn-check" name="btnradio" value="4" id="btnradio5" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-warning" for="btnradio5">Warning</label>

                                            <c:if test="${c.preference == 5}">
                                            <input type="radio" class="btn-check" name="btnradio" value="5" id="btnradio6" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 5}">
                                            <input type="radio" class="btn-check" name="btnradio" value="5" id="btnradio6" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-info" for="btnradio6">Info</label>

                                            <c:if test="${c.preference == 6}">
                                            <input type="radio" class="btn-check" name="btnradio" value="6" id="btnradio7" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 6}">
                                            <input type="radio" class="btn-check" name="btnradio" value="6" id="btnradio7" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-light" for="btnradio7">Light</label>

                                            <c:if test="${c.preference == 7}">
                                            <input type="radio" class="btn-check" name="btnradio" value="7" id="btnradio8" autocomplete="off" checked>
                                            </c:if>
                                            <c:if test="${c.preference != 7}">
                                            <input type="radio" class="btn-check" name="btnradio" value="7" id="btnradio8" autocomplete="off">
                                            </c:if>
                                            <label class="btn btn-outline-dark" for="btnradio8">Dark</label>
                                          </div>
                                    </div>
                                </div>
                            </div>
                            <hr class="my-4" />
                            <div class="form-group col-md-2">
                                <label for="userid">Id</label>
                                <input type="text" value="${c.id}" class="form-control" id="userid" disabled/>
                            </div>
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="login">Login</label>
                                    <input type="text" value="${c.login}" id="login" class="form-control" disabled/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" value="${c.email}" class="form-control" id="email"
                                    placeholder="brown@asher.me" />
                            </div>
                            <hr class="my-4" />
                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="oldpassword">Senha Antiga</label>
                                        <input type="password" class="form-control" id="oldpassword" name="oldpassword" />
                                    </div>
                                    <div class="form-group">
                                        <label for="newpassword">Nova Senha</label>
                                        <input type="password" class="form-control" id="newpassword" name="newpassword"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="confirmpassword">Confirme a Senha</label>
                                        <input type="password" class="form-control" id="confirmpassword" name="confirmpassword"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <p class="mb-2">Dicas para criação de senha</p>
                                    <p class="small text-muted mb-2">Ao criar uma nova senha,
                                        tente seguir todos os requerimentos a seguir:</p>
                                    <ul class="small text-muted pl-4 mb-0">
                                        <li>Mínimo 8 caracteres</li>
                                        <li>Ao menos um caractere especial</li>
                                        <li>Ao menos um número</li>
                                        <li>Não utilize a senha antiga</li>
                                    </ul>
                                </div>
                            </div>
                            <input type="submit" class="btn btn-outline-secondary" value="Salvar">
                        </form>
                    </div>
                </div>
            </div>

        </div>
        

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>
        </c:forEach>
        </main>

</body>

</html>
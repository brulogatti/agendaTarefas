<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%String wrongPassword = (String)request.getAttribute("wrongPassword"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
<style>
    html,
    body {
    height: 100%;
    }

    .form-signin {
    max-width: 330px;
    padding: 1rem;
    }

    .form-signin .form-floating:focus-within {
    z-index: 2;
    }

    .form-signin input[type="text"] {
    margin-bottom: -1px;
    }

    .form-signin input[type="password"] {
    margin-bottom: 10px;
    }

    .alert-user {
    max-width: 500px;
    padding: 1rem;
    }
</style>
</head>
<body>
    
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
          <a class="navbar-brand" href="./login">Agenda</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="./register">Cadastrar Usuário</a>
              </li>
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="./login">Login</a>
              </li>
          </div>
        </div>
    </nav>

    <main class="form-signin w-100 m-auto">
    <form action="<%=request.getContextPath() %>/login" method="post">
        <fieldset>
            <h1>Login de usuário</h1>
            <p>
            	<label>Login:</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Login" aria-label="Login" aria-describedby="basic-addon1" name="login" >
                </div>
            </p>
            <p>
            	<label>Senha:</label>
                <div class="input-group mb-3">
                    <input type="password" class="form-control" placeholder="Senha" aria-label="Senha" aria-describedby="basic-addon1" name="password" >
                </div>
            </p>
            <div class="input-group mb-3">
                <input type="submit" value="Enviar" class="btn btn-outline-secondary" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Enviar">
            </div>
            <div class="input-group mb-3">
            <a class="btn btn-outline-info" href="./register" role="button">Cadastrar novo usuário</a>
            </div>
        </fieldset>
    </form>
    </main>

    <div class="alert-user w-100 m-auto">
	    <div class="alert alert-danger alert-dismissible fade show" role="alert" id="alert-user-div" hidden>
	  		<strong>Senha incorreta!</strong> 
	  		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
	</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>


    <script>
        let element = document.getElementById("alert-user-div");
        let hidden = '<%= wrongPassword %>';
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
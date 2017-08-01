<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>Welcome</title>
  <meta name="viewport" content="width=device-width, initial-scale=1"><link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

  
      <link rel="stylesheet" href="css/style.css">
      <!-- Bootstrap core CSS -->
    <link href="bootstrap-3.3.7/dist/css/bootstrap.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="bootstrap-3.3.7/docs/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/jumbotron.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="bootstrap-3.3.7/docs/assets/js/ie-emulation-modes-warning.js"></script>

    <link href="css/main.css" rel="stylesheet">

  
</head>

<body>
<nav class="navbar navbar-fixed-top navbar-inverse">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <div class="logo">
            <img src="photos/logo.png">
          </div>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <div class="flex">
            <form method="POST" action="PhotoGrid" class="search">
                <input id="search-bar" name="search" type="text" placeholder="Search...">
                <input id="search-button" name="search_submit" type="submit" value="Search!">
                <input type="hidden" value="searchnologin" name="requestor" />
            </form>
            <div class="nav-rest">
              <form method="POST" action="PhotoGrid" class="search-toggle">
                  <input id="search-bar-toggle" name="search" type="text" placeholder="Search...">
                  <input id="search-button-toggle" name="search_submit" type="submit" value="Search!">
                  <input type="hidden" value="searchnologin" name="requestor" />
              </form>
            <form method="post" action="PhotoGrid" class="nav navbar-nav" id="nav">
                <input type="hidden" value="home" name="requestor" />
                <input id="nav-button" type="submit" value="Public"/>
            </form>
            </div>
          </div>
          
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->
  <div class="user">
    <header class="user__header">
        <h1 class="user__title">Welcome to View It</h1>
    </header>
    
    <form method="post" action="Redirect" class="form">
    	<input type="hidden" value="login" name="path" />
    	<button class="btnClick" type="submit">Log into your Account</button>
    </form>
    <form method="post" action="Redirect" class="form">
    	<input type="hidden" value="register" name="path" />
    	<button class="btnClick" type="submit">Register an Account</button>
    </form>
</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="bootstrap-3.3.7/docs/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="bootstrap-3.3.7/docs/dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="bootstrap-3.3.7/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
  	<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src="js/index.js"></script>

</body>
</html>

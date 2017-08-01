<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="bootstrap-3.3.7/docs/favicon.ico">

    <title>Profile</title>

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

    <link rel="stylesheet" type="text/css" href="css/photogrid/normalize.css" />
    <link rel="stylesheet" type="text/css" href="css/photogrid/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/photogrid/pater.css" />
    <script>document.documentElement.className = 'js';</script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
                <input id="search-bar" name="tags" type="text" placeholder="Search...">
                <input id="search-button" name="search_submit" type="submit" value="Search!">
                <input type="hidden" value="search" name="requestor" />
            </form>
            <div class="nav-rest">
              <form method="POST" action="PhotoGrid" class="search-toggle">
                  <input id="search-bar-toggle" name="tags" type="text" placeholder="Search...">
                  <input id="search-button-toggle" name="search_submit" type="submit" value="Search!">
                  <input type="hidden" value="search" name="requestor" />
              </form>
              <div class="nav navbar-nav" id="nav">
                <form method="post" action="PhotoGrid" class="nav navbar-nav" id="nav">
                  <input type="hidden" value="login" name="requestor"/>
                  <input id="nav-button" type="submit" value="Public"/>
              </form>
              <form method="post" action="PhotoGrid" class="nav navbar-nav" id="nav">
                  <input type="hidden" value="shared" name="requestor" />
                  <input id="nav-button" type="submit" value="Shared"/>
              </form>
              <form method="post" action="Profile" class="nav navbar-nav" id="nav">
              	<input type="hidden" value="ownprofile" name="requestor" />
                <input id="nav-button-active" type="submit" value="Profile" />
              </form>
              <form method="post" action="Logout" class="nav navbar-nav" id="nav">
                <input id="logout-item" type="submit" value="Logout" />
              </form>
              <form method="post" action="Redirect" class="nav navbar-nav" id="nav">
              	<input type="hidden" value="upload" name="path" />
                <input id="nav-button" type="submit" value="Upload"/>
              </form>
              </div>
            <form method="post" action="Logout" class="nav navbar-nav nav-right logout" id="nav">
              <input id="nav-button" type="submit" value="Logout" />
            </form>
            </div>
          </div>
          
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->
    


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="bootstrap-3.3.7/docs/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="bootstrap-3.3.7/docs/dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="bootstrap-3.3.7/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
    <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.imagesloaded/3.1.8/imagesloaded.pkgd.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/masonry/3.3.1/masonry.pkgd.min.js"></script>
    
    <script>
    	$(document).ready(function(){
    		
			var $container = $('#album-container');
    		
    		$container.imagesLoaded(function () {
    	        $container.masonry();
    	    });
    	});
    </script>
  </body>
</html>

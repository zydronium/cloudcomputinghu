<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Jumbotron Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="css/jumbotron.css" rel="stylesheet" />
    <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.html">Sollicitatie</a>
          <a class="navbar-brand" href="2a.html">2A</a>
          <a class="navbar-brand" href="2b.html">2B</a>
          <a class="navbar-brand" href="3a.html">3A</a>
          <a class="navbar-brand" href="3b.html">3B</a>
          <a class="navbar-brand" href="4a.html">4A</a>
          <a class="navbar-brand" href="4b.html">4B</a>
          <a class="navbar-brand" href="5a.html">5A</a>
          <a class="navbar-brand" href="5b.html">5B</a>
        </div>
      </div>
    </nav>
    <div class="container">
      <!-- Example row of columns -->
	  <div class="row">
		<div class="col-md-12" id="TaskmanagerPlaceholder">
                    <h1>Tasks manager</h1>
		</div>
	  </div>
      <hr>

      <footer>
        <p>&copy; Company 2014</p>
      </footer>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/moment.min.js"></script>
    <script src="js/bootstrap-datepicker.min.js" type="text/javascript"></script>
    <script>
        $(function () {
            function isEven(n) 
            {
               return (n % 2 == 0);
            }
            
            function writeTaskToPage(project) {
                $.get( "jsontaskmanager?action=task&project="+project, function( data ) {
                    i = 0;
                    var items = [];
                    $.each( data, function( key, val ) {
                        i++;
                        if(isEven(i)) {
                            items.push( "<li id='task" + key + "'><span style='background-color: #00CCFF;' class=\"taskrecord\">" + val + "</span></li>" );
                        }else{
                            items.push( "<li id='task" + key + "'><span style='background-color: #ffffff;' class=\"taskrecord\">" + val + "</span></li>" );
                        }
                        
                    });

                    var html = "<ul>" + items.join( "" ) + "</ul>";
                    $('#open').html(html);
                    $.each( data, function( key, val ) {
                        $('#task' + key).click(function() {
                            $.get( "jsontaskmanager?action=status&task="+key, function( data ) {
                                var taskRecord = document.getElementById('task' + key);
                                var elements = taskRecord.getElementsByClassName("taskrecord");
                                var contentElement = elements[0];
                                contentElement.innerHTML = data.recordKey;
                            }, 'json');
                        });
                    });
                }, 'json');
            }
            
            function goToProjectPage(project) {
                $.get( "jsontaskmanager?action=account", function( data ) {
                    i = 0;
                    var items = [];
                    $.each( data, function( key, val ) {
                        i++;
                        items.push( "<option value='" + key + "'>" + val + "</option>" );
                    });

                    $('#TaskmanagerPlaceholder').html("<h1>Tasks manager</h1><span id=\"overview\">Back to overview</span><h3>Add Task</h3>"
                    +"<input type=\"text\" placeholder=\"give task name\" id=\"name\" />"
                    +"<input type=\"text\" placeholder=\"give task summary\" id=\"summary\" />"
                    +"<input type=\"text\" placeholder=\"give task endtime\" id=\"endtime\" />"
                    +"<select id=\"account\">" + items.join( "" ) + "</ul>"
                    +"<input type=\"button\" id=\"submit\" value=\"Submit\" />"
                    +"<br/>"
                    +"<h3>Project list</h3>"
                    +"<div id=\"open\"></div>");
                    $('#overview').click(function() {
                        startApp();
                    });
                    $('#submit').click(function() {
                        var name = $('#name').val();
                        var summary = $('#summary').val();
                        var account = $('#account').val();
                        $.post( "jsontaskmanager?action=task&project="+project+"&name=" + name + "&summary=" + summary + "&endtime=" + summary + "&account=" + account, function( d ) {
                            writeTaskToPage(project);
                        });
                    });
                    writeTaskToPage(project);
                    $('#endtime').datetimepicker();
                }, 'json');
            }
            
            function writeProjectToPage() {
                $.get( "jsontaskmanager?action=project", function( data ) {
                    i = 0;
                    var items = [];
                    $.each( data, function( key, val ) {
                        i++;
                        if(isEven(i)) {
                            items.push( "<li id='project" + key + "'><span style='background-color: #00CCFF;'>" + val + "</span></li>" );
                        }else{
                            items.push( "<li id='project" + key + "'><span style='background-color: #ffffff;'>" + val + "</span></li>" );
                        }
                    });

                    var html = "<ul>" + items.join( "" ) + "</ul>";
                    $('#open').html(html);
                    $.each( data, function( key, val ) {
                        $('#project' + key).click(function() {
                            goToProjectPage(key);
                        });
                    });
                }, 'json');
                
                $.get( "feed?type=json", function( data ) {
                    i = 0;
                    var items = [];
                    $.each( data, function( key, val ) {
                        i++;
                        if(isEven(i)) {
                            items.push( "<li id='task" + key + "'><span style='background-color: #00CCFF;'>" + val + "</span></li>" );
                        }else{
                            items.push( "<li id='task" + key + "'><span style='background-color: #ffffff;'>" + val + "</span></li>" );
                        }
                    });

                    var html = "<ul>" + items.join( "" ) + "</ul>";
                    $('#feed').html(html);
                }, 'json');
            }
            
            function startApp() {
                $.get( "jsontaskmanager?action=account", function( data ) {
                    i = 0;
                    var items = [];
                    $.each( data, function( key, val ) {
                        i++;
                        items.push( "<option value='" + key + "'>" + val + "</option>" );
                    });

                    $('#TaskmanagerPlaceholder').html("<h1>Tasks manager</h1><h3>Add project</h3>"
                    +"<input type=\"text\" placeholder=\"give project name\" id=\"name\" />"
                    +"<input type=\"text\" placeholder=\"give project summary\" id=\"summary\" />"
                    +"<select id=\"account\">" + items.join( "" ) + "</ul>"
                    +"<input type=\"button\" id=\"submit\" value=\"Submit\" />"
                    +"<br/>"
                    +"<h3>Project list</h3>"
                    +"<div id=\"open\"></div>"
                    +"<h3>Alle openstaande taken</h3>"
                    +"<div id=\"feed\"></div><br />"
                    +"<a href=\"feed?type=rss\" target=\"_blank\">RSS feed</a>");
                    $('#submit').click(function() {
                        var name = $('#name').val();
                        var summary = $('#summary').val();
                        var account = $('#account').val();
                        $.post( "jsontaskmanager?action=project&name=" + name + "&summary=" + summary + "&account=" + account, function( d ) {
                            writeProjectToPage();
                        });
                    });
                    writeProjectToPage();
                }, 'json');
            }
            
            /*$('#submit').click(function() {
                var name = $('#name').val();
                var summary = $('#summary').val();
                var endtime = $('#endtime').val();
                $.post( "jsontaskmanager?name=" + name + "&summary=" + summary + "&endtime=" + endtime, function( data ) {
                    writeTaskToPage();
                }, 'json');
            });*/

            startApp();
        });
    </script>
  </body>
</html>

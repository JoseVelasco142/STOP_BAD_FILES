<%-- 
    Document   : LoggedMain
    Created on : 14-nov-2015, 0:57:09
    Author     : Jose
--%>
<%
    session = request.getSession(false);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>TODO supply a title</title>
    <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="<%= request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="<%= request.getContextPath() %>/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-2.2.0.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.mixitup.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/main.js"></script>
        <script>    
            $( document ).ready(function() {
                SBF.prototype.getFileList();
                
                $('#logout').click(function(){
                    SBF.prototype.logout();
                });
                $('#viewDelete').click(function(){
                    SBF.prototype.deleteFile($("#viewUUID").html());
                    $('#viewClose').click();
                });
                $('#viewClose').click(function(){
                    $('#main').animate({
                        width: "83.3333%",
                        opacity: 1
                    }, 1500);
                    $('#fileViewer').animate({
                        width: "0%",
                        opacity: 0
                    }, 1500);
                });
            });
        </script>
</head>
<body id="loader">
    <nav class="navbar navbar-inverse">
        <label id="title" class="col-xs-9">Stop Bad Files</label>
        <label id="session" class="col-xs-2"><small>Logged as: </small><%=session.getAttribute("uid") %></label>
        <button id="logout" class="btn btn-danger">Log out</button>
    </nav>       
    <div id="main" class="col-md-10 col-md-offset-1 col-lg-10 col-lg-offset-1">
        <div id="header">
            <div class="col-xs-3">
                <a class="col-lg-6 col-md-6 hidden-sm hidden-xs">Filename</a>
                <div class="col-lg-6 col-md-6">
                    <a class="col-xs-6 btn glyphicon glyphicon-sort-by-alphabet sort" data-sort="filename:asc"></a>
                    <a class="col-xs-6 btn glyphicon glyphicon-sort-by-alphabet-alt sort" data-sort="filename:desc"></a>
                </div>
            </div>  
            <div class="col-xs-2">
                <a class="col-xs-6 btn glyphicon glyphicon-menu-up sort" data-sort="size:asc"></a>
                <a class="col-xs-6 btn glyphicon glyphicon-menu-down sort" data-sort="size:desc"></a>
            </div> 
            <div class="col-xs-2">
                <a class="col-xs-6 btn glyphicon glyphicon-sort-by-alphabet sort" data-sort="user:asc"></a>
                <a class="col-xs-6 btn glyphicon glyphicon-sort-by-alphabet-alt sort" data-sort="user:desc"></a>
            </div> 
            <div class="col-xs-3">
                <a class="col-lg-6 col-md-6 hidden-sm hidden-xs">Date</a>
                <div class="col-lg-6 col-md-6 ">
                    <a class="col-xs-6 btn glyphicon glyphicon-sort-by-alphabet sort" data-sort="date:asc"></a>
                    <a class="col-xs-6 btn glyphicon glyphicon-sort-by-alphabet-alt sort" data-sort="date:desc"></a>
                </div>
            </div> 
            <div class="col-xs-2 pager-list"></div> 
        </div>
        <div id="fileList"></div>
    </div>
     <!-- FILE VIEWER -->   
    <div id="fileViewer" class="well-lg col-xs-6">
        <div id="viewUUID" class="hidden"></div>
        <div class="col-xs-12 info-block">
            <div class="col-xs-4 title">Filename</div>
            <div id="viewFilename" class="col-xs-8 file-info"></div>
        </div>
        <div class="col-xs-12 info-block">
            <div class="col-xs-2 title">Size</div>
            <div id="viewSize" class="col-xs-4 file-info"></div>
            <div class="col-xs-2 title">Owner</div>
            <div id="viewOwner" class="col-xs-4 file-info"></div>
        </div>
        <div class="col-xs-12 info-block">
            <div class="col-xs-2 title">Date</div>
            <div id="viewDate" class="col-xs-10 file-info"></div>
        </div>
        <div class="col-xs-12 info-block">
            <div class="col-xs-12 title">Report</div>
            <div id="viewReport" class="col-xs-12 file-info"></div>
        </div>
        <button id="viewDelete" class="btn col-xs-6" type="button">Eliminar</button>
        <button id="viewClose" class="btn col-xs-6" type="button">Cerrar</button>
    </div>       
</body>
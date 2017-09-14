<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<#include "theme.ftl">
    <link rel="stylesheet" href="/css/library_style.css">

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">
                <span class="glyphicon glyphicon-book"></span>
                Online Library
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">


            <form id="search-form" class="nav navbar-form navbar-left">
                <input type="text" class="form-control menu-search-text-input" placeholder="Search" id="query"
                       name="query">
                <button id="btn-search" type="submit" class="btn btn-default menu-search-btn">
                <#--<span class="glyphicon glyphicon-search"></span>-->
                    Search
                </button>

                <!-- Single button -->
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Filter <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" style="padding: 6px">
                        <li>
                            <div class="checkbox">
                                <label><input type="checkbox" value="">Option 1</label>
                            </div>
                        </li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                    </ul>
                </div>

            </form>

            <ul class="nav navbar-nav navbar-right">
            <#if (username??) && (username!="")>
                <li class="dropdown" hidden>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">
                        <span class="glyphicon glyphicon-user"></span>
                    ${username}
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/user">User page</a></li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a href="#" onclick="logout()">Logout</a>
                            <form id="logout_form" action="${"/logout"}" method="post">
                                <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
                            </form>
                        </li>
                    </ul>
                </li>
            <#else>
                <li>
                    <a href="/login">
                        Login
                        <span class="glyphicon glyphicon-log-in"></span>
                    </a>
                </li>
            </#if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div id="content" class="container-fluid">
<#--<#include "content.ftl">-->
</div>

<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script src="/js/main.js"></script>
</body>
</html>
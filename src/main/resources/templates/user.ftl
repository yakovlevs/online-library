<!DOCTYPE html>
<html lang="en">
<head>
    <title>${username}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<#include "theme.ftl">
    <link rel="stylesheet" href="/css/library_style.css">
</head>
<body>
<div class="container-fluid">
    <div class="panel panel-default" style="margin-top: 16px">
        <div class="panel-body">
            <p class="lead">Hello, ${username}!</p>
            <p>You are logged with role(s): ${roles}</p>
            <div class="row">
                <div class="col-lg-1">
                    <a class="btn btn-primary" href="/">Home</a></div>
                <div class="col-lg-offset-1">
                    <form action="${"/logout"}" method="post">
                        <input class="btn btn-primary" type="submit" value="Sign Out"/>
                        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row container" style="margin-bottom: 20px">
        <div class="col-lg-12">
            <ul class="nav nav-pills">
            <#if favorites??>
                <#assign totalFavBooks = favorites?size>
                <li role="presentation" class="active">
                    <a id="fav-tab">Favorites <span id="fav-badge" class="badge">${totalFavBooks}</span></a>
                </li>
            </#if>
            <#if purchased??>
                <#assign totalPurchasedBooks = purchased?size>
                <li role="presentation">
                    <a id="purchased-tab">Purchased books <span class="badge">${totalPurchasedBooks}</span></a>
                </li>
            </#if>
            </ul>
        </div>
    </div>
    <div id="user-fav" class="row">
    <#include "user_fav.ftl">
    </div>
</div>
<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script src="/js/main.js"></script>
</body>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<#include "theme.ftl">
    <link rel="stylesheet" href="/css/library_style.css">
</head>
<body>
<div class="container">
    <h1>Hello ${username}!</h1>
    <p>You are logged with username: ${username} and with roles: ${roles}</p>
    <form action="${"/logout"}" method="post">
        <input type="submit" value="Sign Out"/>
        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
    </form>
<#if favorites??>
    <#assign totalFavBooks = favorites?size>
<p>${totalFavBooks}</p>
    <#if totalFavBooks gt 0>
        <#list favorites as book>
            <#include "book_card.ftl">
        </#list>
    </#if>
</#if>
</div>
<script src="/webjars/jquery-3.2.1.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>
</html>

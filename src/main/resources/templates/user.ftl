<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<#include "theme.ftl">

    <script src="https://code.jquery.com/jquery-3.2.1.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
    <h1>Hello ${username}!</h1>
    <p>You are logged with username: ${username} and with roles: ${roles}</p>
    <form action="${"/logout"}" method="post">
        <input type="submit" value="Sign Out"/>
        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
    </form>
</div>
<script src="/webjars/jquery-3.2.1.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>
</html>

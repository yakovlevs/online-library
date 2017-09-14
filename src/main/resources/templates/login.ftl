<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
    <#include "theme.ftl">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="
        col-xs-8 col-xs-offset-2
        col-sm-8 col-sm-offset-2
        col-md-6 col-md-offset-3
        col-lg-6 col-lg-offset-3
        ">
            <div class="panel panel-default" style="margin-top: 45px">
                <div class="panel-heading">
                    <h3 class="panel-title">Login with Username and Password</h3>
                </div>
                <div class="panel-body">
                <#if logout>
                    <div class="alert alert-success" role="alert">You've been logged out successfully.</div>
                </#if>
                <#if error>
                    <div class="alert alert-danger" role="alert">Invalid username or password</div>
                </#if>
                    <form method="post">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" class="form-control" id="username" placeholder="Username"
                                   name="username">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" id="password" placeholder="Password"
                                   name="password">
                        </div>
                        <button type="submit" class="btn btn-default">Log in</button>
                        <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>
</html>
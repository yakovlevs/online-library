<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="theme-color" content="#158cba">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<#include "theme.ftl">
    <link rel="stylesheet" href="/css/library_style.css">

</head>
<body>
<div id="main-loader">
<#include "overlay.ftl">
</div>
<div id="main-page">
<#include "navbar.ftl">
    <div id="content-loader">
    <#include "overlay.ftl">
    </div>
    <div id="content" class="container-fluid">
    <#include "content.ftl">
    </div>
</div>

<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script src="/js/main.js"></script>
</body>
</html>
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
    <p>
    <#if outSum??>
        OutSum ${outSum},
    </#if>
    <#if invId??>
        InvId ${invId},
    </#if>
    <#if gbookId??>
        Shp_book ${gbookId},
    </#if>
    <#if user??>
        Shp_user ${user}
    </#if>
    </p>
</div>
<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>
</html>
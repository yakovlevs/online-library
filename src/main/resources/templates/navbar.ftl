<nav class="navbar navbar-default" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Online Library</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <#--<li class="active"><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>-->
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Filter <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li class="divider"></li>
                    <li><a href="#">Separated link</a></li>
                    <li class="divider"></li>
                    <li><a href="#">One more separated link</a></li>
                </ul>
            </li>
        </ul>
        <div class="col-sm-3 col-md-3">
            <form id="search-form" class="navbar-form" role="search">
                <div id="navbar-input-group" class="input-group">
                    <input type="text" class="form-control" placeholder="Search" id="query"
                           name="query">
                    <div class="input-group-btn">
                        <button id="btn-search" class="btn btn-default" type="submit">
                            <#--<i class="glyphicon glyphicon-search"></i>-->
                            <span>Search</span>
                        </button>
                    </div>
                </div>
            </form>
        </div>

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
</nav>
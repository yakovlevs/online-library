<div class="
col-xs-12
col-sm-12
col-md-12
col-lg-12
">
<#if numOfBooks?? && (numOfBooks gt 0)>
    <#assign pages =(numOfBooks/booksOnPage)?ceiling> <#--ceiling: Rounds the number upwards (i.e., towards positive infinity)-->
<#--<p>${pages}</p>--> <p>Founded ${numOfBooks}</p>
    <#--<nav aria-label="...">-->
        <#--<ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="#" tabindex="-1">Previous</a>
            </li>

        &lt;#&ndash;<li class="page-item"><a class="page-link" href="#">${page}</a></li>&ndash;&gt;
            <li class="page-item">
                <a href="#" class="page-link dropdown-toggle" data-toggle="dropdown">Page <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <#list 1..pages as page>
                        <li><a href="#">${page}</a></li>
                    </#list>
                </ul>
            </li>

            <li class="page-item">
                <a class="page-link" href="#">Next</a>
            </li>
        </ul>-->
    <div class="btn-group">
        <a href="#" class="btn btn-default">8</a>
        <div class="btn-group">
            <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                Dropdown
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <#list 1..pages as page>
                    <li><a href="#">${page}</a></li>
                </#list>
            </ul>
        </div>
        <a href="#" class="btn btn-default">8</a>
    </div>
</div>
    <#--</nav>-->
</#if>
</div>
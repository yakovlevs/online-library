<div id="paginator" class="
col-xs-12
col-sm-12
col-md-12
col-lg-12
">
<#if numOfBooks?? && (numOfBooks gt 0)>
    <#assign pages =(numOfBooks/booksOnPage)?ceiling>
    <div class="alert alert-dismissible alert-info" style="width:200px;">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>Found books: </strong> ${numOfBooks}
    </div>
    <div class="btn-group">
        <a href="#" class="btn btn-default">Previous</a>
        <div class="btn-group">
            <a id="page_selector" href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                1 <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <#list 1..pages as page>
                    <li><a class="book_page" href="#">${page}</a></li>
                </#list>
            </ul>
        </div>
        <a href="#" class="btn btn-default">Next</a>
    </div>
<#--<p class="text-primary" style="display: inline-block !important;">Founded ${numOfBooks}</p>-->
</div>
<#else>
<div class="row"></div>
</#if>
<div id="paginator" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

<#if searchResult?size gt 0>
    <div class="btn-group">
        <#if currentPage == 0>
            <a id="prev_page" class="btn btn-default" disabled="true">Previous</a>
        <#else>
            <a id="prev_page" class="btn btn-default">Previous</a>
        </#if>
        <div class="btn-group">
            <a id="current_page" href="#" class="btn btn-default">${currentPage+1}</a>
        </div>
        <#if searchResult?size lt booksOnPage>
            <a id="next_page" class="btn btn-default" disabled="true">Next</a>
        <#else>
            <a id="next_page" class="btn btn-default">Next</a>
        </#if>
    </div>
<#else>
    <div class="row"></div>
</#if>
</div>
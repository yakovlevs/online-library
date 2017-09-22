<div id="paginator" class="
col-xs-12
col-sm-12
col-md-12
col-lg-12
">
<#if currentPage??>
    <div class="btn-group">
        <#if (currentPage??) && (currentPage == 0)>
            <a id="prev_page" href="#" class="btn btn-default" disabled = "disabled">Previous</a>
        <#else>
            <a id="prev_page" href="#" class="btn btn-default">Previous</a>
        </#if>
        <div class="btn-group">
            <a id="current_page" class="btn btn-default">${currentPage+1}</a>
        </div>
        <#if (searchResult?size) lt booksOnPage>
            <a id="next_page" href="#" class="btn btn-default" disabled = "disabled">Next</a>
        <#else>
            <a id="next_page" href="#" class="btn btn-default">Next</a>
        </#if>
    </div>
</#if>
<#--<p class="text-primary" style="display: inline-block !important;">Founded ${numOfBooks}</p>-->
</div>

<#if searchResult??>
    <#assign totalBooks = searchResult?size>
<div class="row">
    <#include "paginator.ftl">
</div>

<div class="row">
    <#if totalBooks gt 0>
        <#list searchResult as book>
            <#include "book_card.ftl">
        </#list>
    <#else>
        <div class="col-md-2">
            <h6>Welcome to online library!</h6>
        </div>
    </#if>
</div>

<div class="row">
    <#include "paginator.ftl">
</div>
</#if>

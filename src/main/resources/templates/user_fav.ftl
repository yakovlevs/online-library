<#if favorites??>
    <#assign totalFavBooks = favorites?size>
<div class="container-fluid">
    <#if totalFavBooks gt 0>
    <#list favorites as book>
        <#include "book_card.ftl">
    </#list>
</#if>
</div>
</#if>
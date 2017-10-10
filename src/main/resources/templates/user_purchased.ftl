
<#if purchased??>
    <#assign totalPurchasedBooks = purchased?size>
<div class="container-fluid">
    <#if totalPurchasedBooks gt 0>
    <#list purchased as book>
        <#include "book_card.ftl">
    </#list>
</#if>
</div>
</#if>
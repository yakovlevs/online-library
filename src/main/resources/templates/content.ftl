<#if searchResult??>
    <#assign totalBooks = searchResult?size>
<div class="row">
    <#include "paginator.ftl">
</div>

<div class="row">
    <#if totalBooks gt 0>
        <#list searchResult as book>
            <div class="col-sm-12 col-md-6 col-lg-3">
                <div class="panel panel-default large-book-card">
                    <div class="panel-heading">
                        <h5><b class="hide-title-overflow"> ${book.getTitle()} </b></h5>
                        <p class="hide-title-overflow">
                            <#if book.getSubtitle()??>
                                ${book.getSubtitle()}&zwnj;
                            <#else>
                                &zwnj; &zwnj;<#--invisible placeholder (zero width non-joiner)-->
                            </#if>
                        </p>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <#if book.getThumbnailUrl()??>
                                <img class="book-thumbnail img-rounded center-block" src="${book.getThumbnailUrl()}">
                            </#if>
                        </div>
                        <div class="row">
                            <div class="container-fluid">
                                <#if book.getWebReaderLink()??>
                                    <p>
                                        <a class="btn-web-reader btn btn-primary center-block" role="button"
                                           href="${book.getWebReaderLink()}">Web Reader</a>
                                    </p>
                                </#if>
                                <#if book.getAuthors()??>
                                    <p class="hide-title-overflow"><span class="text-primary">
                                        <#if book.getAuthors()?size gt 1>
                                            Authors:
                                        <#else>
                                            Author:
                                        </#if>
                                    </span>
                                        <#list  book.getAuthors() as author>
                                        ${author}<#if author?is_last>. <#else>, </#if>
                                        </#list>
                                    </p>
                                <#else>
                                    <p><span class="text-primary">Author:</span> ...</p>
                                </#if>
                                <#if book.getCategories()??>
                                    <p class="hide-title-overflow"><span class="text-primary">
                                        <#if book.getCategories()?size gt 1>
                                            Categories:
                                        <#else>
                                            Category:
                                        </#if>
                                    </span>
                                        <#list  book.getCategories() as categorie>
                                        ${categorie}<#if categorie?is_last>. <#else>, </#if>
                                        </#list>
                                    </p>
                                <#else>
                                    <p><span class="text-primary hide-title-overflow">Category:</span> ...</p>
                                </#if>
                                <#if book.getPublisher()??>
                                    <p class="hide-description-overflow hide-title-overflow"><span
                                            class="text-primary">Publisher:</span> ${book.getPublisher()}</p>
                                <#else>
                                    <p><span class="text-primary">Publisher:</span> ...</p>
                                </#if>
                                <#if book.getPublishedDate()??>
                                    <p class="hide-description-overflow hide-title-overflow"><span
                                            class="text-primary">Published date:</span> ${book.getPublishedDate()}</p>
                                <#else>
                                    <p><span class="text-primary hide-title-overflow">Published date:</span> ...</p>
                                </#if>
                                <#if book.getPageCount()??>
                                    <p class="hide-description-overflow"><span
                                            class="text-primary">Page count:</span> ${book.getPageCount()}</p>
                                <#else>
                                    <p><span class="text-primary">Page count:</span> ...</p>
                                </#if>
                                <#if book.getAverageRating()??>
                                    <p class="hide-title-overflow"><span
                                            class="text-primary">Rating:</span> ${book.getAverageRating()}</p>
                                <#else>
                                    <p><span class="text-primary">Rating:</span> ...</p>
                                </#if>
                                <#if book.getLanguage()??>
                                    <p class="hide-title-overflow"><span
                                            class="text-primary">Language:</span> ${book.getLanguage()}</p>
                                <#else>
                                    <p><span class="text-primary">Language:</span> ...</p>
                                </#if>
                                <#if book.getSaleability()??>
                                    <#if book.getSaleability()>
                                        <p class="hide-title-overflow"><span
                                                class="text-primary">Non for sale</span></p>
                                    <#else>
                                        <p class="hide-title-overflow"><span
                                                class="text-primary">For sale</span></p>
                                    </#if>
                                <#else>
                                    <p><span class="text-primary">Free:</span> ...</p>
                                </#if>
                                <#if book.getDescription()??>
                                    <p class="hide-description-overflow"><span
                                            class="text-primary">Description:</span> ${book.getDescription()}</p>
                                <#else>
                                    <p><span class="text-primary">Description:</span> ...</p>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#list>

    <#else>
        <div class="col-md-2">
            <h6>Not found</h6>
        </div>
    </#if>
</div>
</#if>

<#--
<div class="col-md-6">
    <div class="panel panel-default">
        <div class="panel-heading">
                <span class="hideTitleOverflow">
                ${book.getTitle()}
                </span>
        </div>
        <div class="panel-body">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-5 col-md-3">
                        <img class="thumbnail bookThumbnail" src="${book.getThumbnailUrl()}">
                    </div>
                <#if book.getWebReaderLink()??>
                    <div class="col-sm-7 col-md-9">
                        <a href="${book.getWebReaderLink()}">Web Reader</a>
                    </div>
                </#if>
                </div>

                <div class="row">
                <#if book.getDescription()??>
                    <h6>Description:</h6>
                    <div class="hideDescriptionOverflow">
                        <p>${book.getDescription()}</p>

                    </div>
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>-->

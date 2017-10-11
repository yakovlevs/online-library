<!DOCTYPE html>
<html lang="en">
<head>
    <title>${book.getTitle()}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<#include "theme.ftl">
    <script src="https://code.jquery.com/jquery-3.2.1.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="container-fluid col-lg-12">
            <div class="panel panel-default book-card">
                <div class="panel-heading">
                    <h5><a href="/${book.getId()}"><b class="hide-title-overflow">${book.getTitle()}</b></a></h5>
                    <p class="hide-title-overflow"> ${book.getSubtitle()}&zwnj; </p>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <a href="/${book.getId()}">
                            <img class="book-thumbnail img-rounded img-bordered center-block"
                            <#if book.getThumbnailUrl()?? && book.getThumbnailUrl() !="">
                                 src="${book.getThumbnailUrl()}"
                            <#else>
                                 src="img/img_placeholder.jpg"
                            </#if>>
                        </a>
                    </div>
                    <div class="row">
                        <div class="container-fluid">
                            <div class="row well well-sm">
                                <div class="col-lg-12">
                                    <div class="container-fluid text-center">
                                    <#if book.getPdfLink() != "">
                                    <a href="${book.getPdfLink()}" class="text-success">
                                    <#else>
                                    <a class="text-muted">
                                    </#if>
                                        PDF
                                    </a>
                                    <#if book.getEpubLink() != "">
                                    <a href="${book.getEpubLink()}" class="text-success">
                                    <#else>
                                    <a class="text-muted">
                                    </#if>
                                        EPUB
                                    </a>
                                    <#if book.getWebReaderLink() != "">
                                    <a href="${book.getWebReaderLink()}"
                                       class="text-success">
                                    <#else>
                                    <a class="text-muted">
                                    </#if>
                                        Web Reader
                                    </a></div>
                                </div>
                            </div>
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
                            <p class="hide-description-overflow hide-title-overflow"><span
                                    class="text-primary">Publisher:</span> ${book.getPublisher()}</p>
                            <p class="hide-description-overflow hide-title-overflow"><span
                                    class="text-primary">Published date:</span> ${book.getPublishedDate()}</p>
                            <p class="hide-description-overflow"><span
                                    class="text-primary">Page count:</span> ${book.getPageCount()}</p>
                            <p class="hide-title-overflow"><span
                                    class="text-primary">Rating:</span> ${book.getAverageRating()}</p>
                            <p class="hide-title-overflow"><span
                                    class="text-primary">Language:</span> ${book.getLanguage()}</p>
                            <p class="hide-description-overflow"><span
                                    class="text-primary">Description:</span> ${book.getDescription()}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</body>
</html>
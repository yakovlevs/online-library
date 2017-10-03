<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
    <div class="panel panel-default book-card">
        <div class="panel-heading">
            <h5><a href="/${book.getId()}"><b
                    class="hide-title-overflow"> ${book.getTitle()} </b></a></h5>
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
            <#if (username??) && (username!="")>
                <div id="fav-alert">

                        <#if book.isFavorite()>
                        <a id="remove-fav-button" class="btn btn-danger fav-btn center-block" name="${book.getId()}">
                            <span class="glyphicon glyphicon-minus"></span>
                            <form id="add_fav">
                                <input id="csrf" name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
                                <input id="googleBookId" name="googleBookId" type="hidden" value="${book.getId()}">
                            </form>
                        <#else>
                        <a id="add-fav-button" class="btn btn-primary fav-btn center-block" name="${book.getId()}">
                            <span class="glyphicon glyphicon-plus"></span>
                            <form id="add_fav">
                                <input id="csrf" name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
                                <input id="googleBookId" name="googleBookId" type="hidden" value="${book.getId()}">
                            </form>
                        </#if>
                    </a>
                </div>
            </#if>
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
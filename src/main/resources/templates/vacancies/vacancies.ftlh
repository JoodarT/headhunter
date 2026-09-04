<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.mainLayout title=springMacroRequestContext.getMessage('page.vacancies.title') fluid=true>
<div class="mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
        <h2><@spring.message "vacancies.heading"/><#if company?? && company?has_content> &middot; ${company}</#if></h2>
        <div class="d-flex gap-2 align-items-center">
            <#if sort?? && sort == "responses">
            <a href="/vacancies?sort=responses&amp;direction=<#if direction?? && direction == "asc">desc<#else>asc</#if>"
               class="btn btn-outline-secondary btn-sm">
                <@spring.message "vacancies.sort.responses"/> <#if direction?? && direction == "asc">▲<#else>▼</#if>
            </a>
            <a href="/vacancies" class="btn btn-outline-secondary btn-sm"><@spring.message "vacancies.sort.reset"/></a>
            <#elseif sort?? && sort == "date">
            <a href="/vacancies?sort=date&amp;direction=<#if direction?? && direction == "asc">desc<#else>asc</#if>"
               class="btn btn-outline-secondary btn-sm">
                <@spring.message "vacancies.sort.date"/> <#if direction?? && direction == "asc">▲<#else>▼</#if>
            </a>
            <a href="/vacancies" class="btn btn-outline-secondary btn-sm"><@spring.message "vacancies.sort.reset"/></a>
            <#else>
            <a href="/vacancies?sort=responses&amp;direction=desc" class="btn btn-outline-secondary btn-sm">
                <@spring.message "vacancies.sort.byResponses"/>
            </a>
            <a href="/vacancies?sort=date&amp;direction=desc" class="btn btn-outline-secondary btn-sm">
                <@spring.message "vacancies.sort.byDate"/>
            </a>
        </#if>
        <#if navUser?? && navUser.role?? && navUser.role.role == "EMPLOYER">
        <a href="/vacancies/create" class="btn btn-success"><@spring.message "vacancies.create"/></a>
        <#else>
        <a href="#" class="btn btn-success disabled" aria-disabled="true" tabindex="-1"><@spring.message "vacancies.create"/></a>
    </#if>

        </div>
        <form action="/vacancies" method="get" class="mb-4">
            <div class="input-group">
                <input type="text" name="search" class="form-control" placeholder="<@spring.message "vacancies.search.placeholder"/>" value="${(search)!''}">
                <button class="btn btn-primary" type="submit"><@spring.message "common.search"/></button>
            </div>
        </form>
            </div>

    <#if company?? && company?has_content>
    <div class="alert alert-secondary d-flex justify-content-between align-items-center py-2">
        <span><@spring.message "vacancies.filter.company"/> <strong>${company}</strong></span>
        <a href="/vacancies" class="btn btn-sm btn-outline-secondary"><@spring.message "vacancies.filter.reset"/></a>
    </div>
</#if>

<div class="row">
        <#if vacancies?? && (vacancies?size > 0)>
        <#list vacancies as vacancy>
        <div class="col-12 mb-3">
            <div class="card shadow-sm border-0">
                <div class="card-body d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3">
                    <div class="flex-grow-1">
                        <h5 class="card-title text-primary mb-1">
                            <a href="/vacancies/${vacancy.id}" class="text-decoration-none">${vacancy.title!""}</a>
                        </h5>
                        <#if vacancy.company?? && vacancy.company?has_content>
                        <p class="card-subtitle mb-1 text-muted">
                            <a href="/vacancies?company=${vacancy.company?url('UTF-8')}" class="text-decoration-none text-muted">${vacancy.company}</a>
                        </p>
                    </#if>
                    <p class="card-text text-muted mb-2">
                            <small>
                                <#if vacancy.categoryName??>
                                <@spring.message "vacancies.category"/> ${vacancy.categoryName}
                                <#elseif vacancy.categoryId??>
                                <@spring.message "vacancies.category.id"/> ${vacancy.categoryId}
                                <#else>
                                <@spring.message "vacancies.category"/> <@spring.message "vacancies.category.none"/>
                            </#if>
                                &middot; <@spring.messageArgs "vacancies.responsesCount", [vacancy.responsesCount!0]/>
                            </small>
                        </p>
                        <p class="card-text text-secondary mb-0" style="display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">
                            ${vacancy.description!""}
                        </p>
                    </div>

                    <div class="d-flex flex-column flex-md-row align-items-md-center gap-2 flex-shrink-0">
                        <span class="text-success fw-bold fs-5">
                            <#if vacancy.salary??>
                                ${vacancy.salary} <@spring.message "common.currency"/>
                            <#else>
                                <@spring.message "vacancies.salary.negotiable"/>
                            </#if>
                        </span>
                        <a href="/vacancies/${vacancy.id}" class="btn btn-outline-primary btn-sm"><@spring.message "vacancies.details"/></a>
                    </div>
                </div>
            </div>
        </div>
    </#list>
    <#else>
    <div class="col-12">
        <div class="alert alert-info text-center py-4">
            <@spring.message "vacancies.empty"/>
        </div>
    </div>
</#if>
</div>

<#if totalPages?? && totalPages gt 1>
<nav class="mt-4" aria-label="<@spring.message "vacancies.pager.label"/>">
    <ul class="pagination justify-content-center">
        <li class="page-item <#if currentPage lte 0>disabled</#if>">
            <a class="page-link" href="/vacancies?page=${currentPage - 1}<#if sort??>&amp;sort=${sort}&amp;direction=${direction!'desc'}</#if><#if company??>&amp;company=${company?url('UTF-8')}</#if><#if search??>&amp;search=${search?url('UTF-8')}</#if>"><@spring.message "vacancies.pager.prev"/></a>
        </li>
        <#list 0..(totalPages - 1) as p>
        <li class="page-item <#if p == currentPage>active</#if>">
            <a class="page-link" href="/vacancies?page=${p}<#if sort??>&amp;sort=${sort}&amp;direction=${direction!'desc'}</#if><#if company??>&amp;company=${company?url('UTF-8')}</#if><#if search??>&amp;search=${search?url('UTF-8')}</#if>">${p + 1}</a>
        </li>
    </#list>
    <li class="page-item <#if currentPage gte (totalPages - 1)>disabled</#if>">
        <a class="page-link" href="/vacancies?page=${currentPage + 1}<#if sort??>&amp;sort=${sort}&amp;direction=${direction!'desc'}</#if><#if company??>&amp;company=${company?url('UTF-8')}</#if><#if search??>&amp;search=${search?url('UTF-8')}</#if>"><@spring.message "vacancies.pager.next"/></a>
    </li>
    </ul>
</nav>
</#if>

</div>
</@layout.mainLayout>

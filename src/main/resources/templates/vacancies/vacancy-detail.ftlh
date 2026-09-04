<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<#assign pageTitle = (vacancy.title)!springMacroRequestContext.getMessage('page.vacancyDetail.title')>
<@layout.mainLayout title=pageTitle>
<div class="row justify-content-center">
    <div class="col-md-9 bg-white p-4 rounded shadow-sm">

        <div class="d-flex justify-content-between align-items-start mb-4 border-bottom pb-3">
            <div>
                <h2 class="mb-1 text-primary">${vacancy.title!springMacroRequestContext.getMessage('vacancyDetail.title.none')}</h2>
                <#if vacancy.company?? && vacancy.company?has_content>
                <h5 class="text-secondary mb-2">${vacancy.company}</h5>
            </#if>
            <div class="d-flex align-items-center gap-3 mt-2">
                    <span class="fs-4 fw-bold text-success">
                        <#if vacancy.salary??>
                            ${vacancy.salary} <@spring.message "common.currency"/>
                        <#else>
                            <@spring.message "vacancyDetail.salary.none"/>
                        </#if>
                    </span>
                    <span class="badge bg-secondary">
                        👁 <@spring.messageArgs "vacancyDetail.views", [vacancy.views!0]/>
                    </span>
                </div>
            </div>

            <#if isOwner?? && isOwner>
            <div class="d-flex gap-2">
                <a href="/vacancies/${vacancy.id}/edit" class="btn btn-outline-primary">
                    <@spring.message "common.edit"/>
                </a>

                <form action="/vacancies/${vacancy.id}/delete" method="post" style="display:inline;">
                    <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>
                <button type="submit" class="btn btn-outline-danger"><@spring.message "common.delete"/></button>
                </form>
            </div>
        </#if>
    </div>

    <#if isApplicant?? && isApplicant>
    <div class="mb-4">
        <#if availableResumes?? && (availableResumes?size > 0)>
        <form action="/vacancies/${vacancy.id}/respond" method="post" class="d-flex flex-wrap gap-2 align-items-center">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>
        <#if (availableResumes?size > 1)>
        <select name="resumeId" class="form-select" style="max-width: 300px;" required>
            <#list availableResumes as resume>
            <option value="${resume.id}">${resume.title!("№" + resume.id)}</option>
        </#list>
        </select>
        <#else>
        <input type="hidden" name="resumeId" value="${availableResumes[0].id}"/>
    </#if>
    <button type="submit" class="btn btn-success"><@spring.message "vacancyDetail.respond"/></button>
    </form>
    <#elseif myResumes?? && (myResumes?size > 0)>
    <div class="alert alert-info mb-0"><@spring.message "vacancyDetail.respond.allUsed"/></div>
    <#else>
    <div class="alert alert-warning mb-0">
        <@spring.message "vacancyDetail.respond.needResume"/> <a href="/resumes/create"><@spring.message "vacancyDetail.respond.needResume.link"/></a>.
    </div>
</#if>
</div>
</#if>

<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6"><@spring.message "vacancyDetail.section.category"/></h5>
        <p class="lead fw-normal">
            <#if vacancy.categoryName??>
            ${vacancy.categoryName}
            <#elseif vacancy.categoryId??>
            <@spring.messageArgs "vacancyDetail.category.numbered", [vacancy.categoryId]/>
            <#else>
            <@spring.message "vacancyDetail.category.none"/>
        </#if>
        </p>
    </div>

    <div class="mb-4">
        <h5 class="text-muted text-uppercase fs-6"><@spring.message "vacancyDetail.section.description"/></h5>
        <div class="p-3 bg-light rounded border">
            <p class="mb-0" style="white-space: pre-line;">${vacancy.description!springMacroRequestContext.getMessage('vacancyDetail.description.none')}</p>
        </div>
    </div>

    <div class="mt-4 pt-3 border-top">
        <a href="/resumes" class="btn btn-outline-secondary">← <@spring.message "common.back"/></a>
    </div>

</div>
</div>
</@layout.mainLayout>

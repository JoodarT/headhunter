<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.mainLayout title=springMacroRequestContext.getMessage('page.responses.title')>
<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0"><#if isEmployer><@spring.message "responses.heading.employer"/><#else><@spring.message "responses.heading.applicant"/></#if></h2>
            <a href="/profile" class="btn btn-outline-secondary btn-sm"><@spring.message "responses.backToProfile"/></a>
        </div>

        <#if responses?? && responses?has_content>
        <#list responses as response>
        <div class="card mt-3">
            <div class="card-body d-flex justify-content-between align-items-start">
                <div>
                    <a href="/vacancies/${response.vacancyId}" class="text-decoration-none fw-semibold">${response.vacancyTitle!""}</a>
                    <div class="text-muted small mt-1">${response.company!springMacroRequestContext.getMessage('responses.company.none')}</div>
                    <#if isEmployer>
                    <div class="small mt-1">
                        <strong><@spring.message "responses.applicant"/></strong> ${response.applicantName!springMacroRequestContext.getMessage('responses.applicant.none')}
                        &middot;
                        <a href="/resumes/${response.resumeId}" class="text-decoration-none">${response.resumeTitle!""}</a>
                    </div>
                    <#else>
                    <div class="small mt-1">
                        <strong><@spring.message "responses.resume"/></strong> <a href="/resumes/${response.resumeId}" class="text-decoration-none">${response.resumeTitle!""}</a>
                    </div>
                </#if>
                </div>
                <#if response.confirmation?? && response.confirmation>
                <span class="badge bg-success"><@spring.message "responses.status.confirmed"/></span>
                <#else>
                <span class="badge bg-secondary"><@spring.message "responses.status.pending"/></span>
            </#if>
            </div>
        </div>
        </#list>
        <#else>
        <p class="text-muted">
            <#if isEmployer><@spring.message "responses.empty.employer"/><#else><@spring.message "responses.empty.applicant"/></#if>
        </p>
    </#if>
    </div>
</div>
</@layout.mainLayout>

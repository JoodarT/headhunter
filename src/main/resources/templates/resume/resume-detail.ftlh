<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<#function contactTypeLabel typeName>
    <#return springMacroRequestContext.getMessage("contactType." + typeName, typeName)>
</#function>
<#assign pageTitle = (resume.title)!springMacroRequestContext.getMessage('page.resumeDetail.title')>
<@layout.mainLayout title=pageTitle>
<div class="row justify-content-center">
    <div class="col-md-9 bg-white p-4 rounded shadow-sm">

        <div class="d-flex justify-content-between align-items-start mb-4 border-bottom pb-3">
            <div>
                <h2 class="mb-1 text-primary">${resume.title!springMacroRequestContext.getMessage('resumeDetail.title.none')}</h2>
                <#if resume.applicantName??>
                <h5 class="text-secondary mb-2">${resume.applicantName}</h5>
            </#if>
            <div class="d-flex align-items-center gap-3">
                    <span class="fs-4 fw-bold text-success">
                        <#if resume.expectedSalary??>
                            ${resume.expectedSalary} <@spring.message "common.currency"/>
                        <#else>
                            <@spring.message "resumeDetail.salary.none"/>
                        </#if>
                </span>
                <#if resume.createdDate??>
                <small class="text-muted"><@spring.message "resumeDetail.published"/> ${resume.createdDate}</small>
            </#if>
        </div>
    </div>

    <#if isOwner?? && isOwner>
    <div class="d-flex gap-2">
        <a href="/resumes/${resume.id}/edit" class="btn btn-outline-primary">
            <i class="bi bi-pencil"></i> <@spring.message "common.edit"/>
        </a>

        <form action="/resumes/${resume.id}/delete" method="post" style="display:inline;">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>
        <button type="submit" class="btn btn-outline-danger"><@spring.message "common.delete"/></button>
        </form>
    </div>
</#if>
</div>

<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6"><@spring.message "resumeDetail.section.category"/></h5>
    <p class="lead fw-normal">${resume.categoryName!springMacroRequestContext.getMessage('resumeDetail.category.none')}</p>
</div>

<#if resume.skills?? && resume.skills?has_content>
<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6"><@spring.message "resumeDetail.section.skills"/></h5>
    <div class="p-3 bg-light rounded border">
        <p class="mb-0" style="white-space: pre-line;">${resume.skills}</p>
    </div>
</div>
</#if>

<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6 mb-3"><@spring.message "resumeDetail.section.experience"/></h5>
    <#if resume.experiences?? && (resume.experiences?size > 0)>
    <#list resume.experiences as exp>
    <div class="card mb-3 border-0 bg-light p-3">
        <div class="d-flex justify-content-between align-items-start">
            <h5 class="mb-1 text-dark">${exp.position!}</h5>
            <#if exp.period??>
            <span class="badge bg-secondary">${exp.period}</span>
        </#if>
    </div>
    <h6 class="text-primary mb-2">${exp.companyName!}</h6>
    <#if exp.responsibilities?? && exp.responsibilities?has_content>
    <p class="mt-2 mb-0 text-secondary" style="white-space: pre-line;">${exp.responsibilities}</p>
</#if>
</div>
</#list>
<#else>
<p class="text-muted italic"><@spring.message "resumeDetail.experience.none"/></p>
</#if>
</div>

<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6 mb-3"><@spring.message "resumeDetail.section.education"/></h5>
    <#if resume.educations?? && (resume.educations?size > 0)>
    <#list resume.educations as edu>
    <div class="card mb-2 border-0 bg-light p-3">
        <h5 class="mb-1">${edu.institution!}</h5>
        <p class="mb-0 text-secondary">
            ${edu.faculty!}
            <#if edu.enrollmentYear?? && edu.graduationYear??>
            <strong class="ms-1"><@spring.messageArgs "resumeDetail.education.years", [edu.enrollmentYear?c, edu.graduationYear?c]/></strong>
            <#elseif edu.graduationYear??>
            <strong class="ms-1"><@spring.messageArgs "resumeDetail.education.yearOne", [edu.graduationYear?c]/></strong>
        </#if>
        </p>
    </div>
</#list>
<#else>
<p class="text-muted italic"><@spring.message "resumeDetail.education.none"/></p>
</#if>
</div>

<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6 mb-3"><@spring.message "resumeDetail.section.contacts"/></h5>
    <#if resume.contactInfos?? && resume.contactInfos?has_content>
    <div class="row g-2">
        <#list resume.contactInfos as c>
        <div class="col-md-6">
            <strong>${contactTypeLabel(c.typeName)}:</strong>
            <#if c.typeName == "EMAIL">
            <a href="mailto:${c.value}">${c.value}</a>
            <#else>
            ${c.value}
            </#if>
        </div>
        </#list>
    </div>
    <#else>
    <p class="text-muted italic"><@spring.message "resumeDetail.contacts.none"/></p>
    </#if>
</div>

<div class="mt-4 pt-3 border-top">
    <a href="/vacancies" class="btn btn-outline-secondary">← <@spring.message "common.back"/></a>
</div>

</div>
</div>
</@layout.mainLayout>

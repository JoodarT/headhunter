<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.mainLayout title=springMacroRequestContext.getMessage('page.resumes.title') fluid=true>
<div class="container-fluid mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><@spring.message "resumes.heading"/></h2>
        <#if navUser?? && navUser.role?? && navUser.role.role == "APPLICANT">
        <a href="/resumes/create" class="btn btn-success"><@spring.message "resumes.create"/></a>
        <#else>
        <a href="#" class="btn btn-success disabled" aria-disabled="true" tabindex="-1"><@spring.message "resumes.create"/></a>
    </#if>
    </div>

    <form action="/resumes" method="get" class="mb-4">
        <div class="input-group">
            <input type="text" name="search" class="form-control" placeholder="<@spring.message "resumes.search.placeholder"/>" value="${(search)!''}">
            <button class="btn btn-primary" type="submit"><@spring.message "common.search"/></button>
        </div>
    </form>

    <div class="row">
        <#if resumes?? && resumes?has_content>
        <#list resumes as resume>
        <div class="col-md-12 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="/resumes/${resume.id}" class="text-decoration-none">${(resume.title)!""}</a>
                    </h5>
                    <h6 class="card-subtitle mb-2 text-muted">${(resume.applicantName)!springMacroRequestContext.getMessage('resumes.applicant.anon')}</h6>

                    <p class="card-text mb-1">
                        <strong><@spring.message "resumes.category"/></strong> ${(resume.categoryName)!springMacroRequestContext.getMessage('resumes.category.none')}
                    </p>

                    <p class="card-text mb-1">
                        <strong><@spring.message "resumes.skills"/></strong> ${(resume.skills)!springMacroRequestContext.getMessage('resumes.skills.none')}
                    </p>

                    <p class="card-text text-success fw-bold mt-2">
                        <strong><@spring.message "resumes.salary"/></strong> ${(resume.expectedSalary?string.number)!0} <@spring.message "common.currency"/>
                    </p>
                </div>

                <#if currentUser?? && resume.userId?? && resume.userId == currentUser.id>
                <a href="/resumes/${resume.id}/edit" class="btn btn-sm btn-outline-primary"><@spring.message "common.edit"/></a>

                <form action="/resumes/${resume.id}/delete" method="post" class="d-inline">
                    <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>
                <button type="submit" class="btn btn-sm btn-outline-danger"><@spring.message "common.delete"/></button>
                </form>
            </#if>
            </div>
        </div>
    </#list>
    <#else>
    <div class="col-12">
        <div class="alert alert-info"><@spring.message "resumes.empty"/></div>
    </div>
</#if>
</div>
</div>
</@main.mainLayout>

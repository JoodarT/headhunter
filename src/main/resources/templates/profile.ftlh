<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.mainLayout title=springMacroRequestContext.getMessage('page.profile.title')>
<div class="row justify-content-center">
    <div class="col-md-8 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4"><@spring.message "profile.heading"/></h2>
        <#if user??>
        <div class="card mb-3">
            <div class="card-body">
                <div class="row align-items-center">

                    <div class="col-md-4 text-center border-end pe-md-4">
                        <div class="mb-3">
                            <#if user.avatarUrl?? && user.avatarUrl?has_content>
                            <img src="/uploads/avatars/${user.avatarUrl}" alt="<@spring.message "profile.avatar.alt"/>"
                                 class="rounded-circle img-thumbnail shadow-sm"
                                 style="width: 150px; height: 150px; object-fit: cover;">
                            <#else>
                            <img src="/images/default-avatar.png"
                                 alt="<@spring.message "profile.avatar.default"/>"
                                 class="rounded-circle img-thumbnail shadow-sm"
                                 style="width: 150px; height: 150px; object-fit: cover;">
                        </#if>
                    </div>

                    <form action="/profile/avatar/${user.id}" method="post" enctype="multipart/form-data">
                        <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </#if>
                    <div class="mb-2">
                        <input type="file" name="file" class="form-control form-control-sm" accept="image/*" required>
                    </div>
                    <button type="submit" class="btn btn-sm btn-outline-primary w-100"><@spring.message "profile.avatar.update"/></button>
                    </form>
                </div>

                <div class="col-md-8 ps-md-4 mt-3 mt-md-0">
                    <h4 class="card-title mb-3">${user.name!springMacroRequestContext.getMessage('profile.name.none')}</h4>
                    <p class="card-text mb-2"><strong><@spring.message "profile.email"/>:</strong> ${user.email!""}</p>
                    <p class="card-text mb-2"><strong><@spring.message "profile.phone"/>:</strong> ${user.phone!springMacroRequestContext.getMessage('profile.phone.none')}</p>
                    <p class="card-text mb-3"><strong><@spring.message "profile.accountType"/>:</strong>
                        <span class="badge bg-info text-dark">${(user.role.role)!""}</span>
                    </p>
                    <a href="/profile/edit" class="btn btn-warning"><@spring.message "profile.edit"/></a>
                    <a href="/responses" class="btn btn-outline-primary ms-2"><@spring.message "profile.responses"/></a>
                </div>

            </div>
        </div>
    </div>
    <#else>
    <div class="alert alert-warning"><@spring.message "profile.notFound"/></div>
</#if>

    <#if myVacancies??>
    <div class="card mt-3">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0"><@spring.message "profile.myVacancies"/> (${myVacancies?size})</h4>
                <a href="/vacancies/create" class="btn btn-success btn-sm"><@spring.message "profile.createVacancy"/></a>
            </div>
            <#if myVacancies?size gt 0>
            <ul class="list-group list-group-flush">
                <#list myVacancies as vacancy>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <a href="/vacancies/${vacancy.id}" class="text-decoration-none">${vacancy.title!""}</a>
                    <span class="text-muted small"><@spring.messageArgs "profile.responsesCount", [vacancy.responsesCount!0]/></span>
                </li>
            </#list>
            </ul>
            <#else>
            <p class="text-muted mb-0"><@spring.message "profile.noVacancies"/></p>
        </#if>
        </div>
    </div>
</#if>

    <#if myResumes??>
    <div class="d-flex justify-content-between align-items-center mt-4 mb-3">
        <h4 class="mb-0"><@spring.message "profile.myResumes"/> (${myResumes?size})</h4>
        <a href="/resumes/create" class="btn btn-success btn-sm"><@spring.message "profile.createResume"/></a>
    </div>
    <#if myResumes?size gt 0>
    <#list myResumes as resume>
    <#assign resumeUpdatedAt = (resume.updatedDate)!(resume.createdDate)!"">
    <div class="card mt-3">
        <div class="card-body d-flex justify-content-between align-items-start">
            <div>
                <a href="/resumes/${resume.id}" class="text-decoration-none fw-semibold">${resume.title!""}</a>
                <div class="text-muted small mt-1">
                    <span>${resume.categoryName!springMacroRequestContext.getMessage('profile.resume.noCategory')}</span>
                    <#if resume.skills?? && resume.skills?has_content>
                    <span> &middot; ${resume.skills}</span>
                </#if>
                </div>
                <#if resume.expectedSalary?? && (resume.expectedSalary > 0)>
                <div class="text-success small fw-bold mt-1">${resume.expectedSalary?string.number} <@spring.message "common.currency"/></div>
            </#if>
            </div>
            <span class="text-muted small text-nowrap ms-3">
                <@spring.message "profile.resume.updated"/> <#if resumeUpdatedAt?is_date_like>${resumeUpdatedAt?string("dd.MM.yyyy HH:mm")}<#else>—</#if>
            </span>
        </div>
    </div>
    </#list>
    <#else>
    <p class="text-muted mb-0"><@spring.message "profile.noResumes"/></p>
</#if>
</#if>
</div>
</div>
</@layout.mainLayout>

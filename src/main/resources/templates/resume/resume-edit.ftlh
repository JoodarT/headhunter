<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#function contactTypeLabel typeName>
    <#return springMacroRequestContext.getMessage("contactType." + typeName, typeName)>
</#function>
<#function getContactValue contactInfos typeId>
    <#list contactInfos![] as c>
        <#if c.contactTypeId?? && c.contactTypeId == typeId>
            <#return c.value!''>
        </#if>
    </#list>
    <#return ''>
</#function>
<@main.mainLayout title=springMacroRequestContext.getMessage('page.resumeEdit.title')>
<div class="container mt-4">
    <h2><@spring.message "resumeEdit.heading"/></h2>

    <#if error??>
    <div class="alert alert-danger" role="alert">
        ${error}
    </div>
</#if>

<form action="/resumes/${resume.id}/edit" method="post" id="resumeForm">
        <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </#if>

    <div class="card mb-4">
        <div class="card-header fw-bold"><@spring.message "resumeForm.section.main"/></div>
        <div class="card-body">
            <div class="mb-3">
                <label for="title" class="form-label"><@spring.message "resumeForm.title"/></label>
                <input type="text" class="form-control" id="title" name="title" value="${(resume.title)!''}" required>
            </div>

            <div class="mb-3">
                <label for="categoryId" class="form-label"><@spring.message "resumeForm.category"/></label>
                <select class="form-select" id="categoryId" name="categoryId" required>
                    <option value="" disabled <#if !resume.categoryId??>selected</#if>><@spring.message "resumeForm.category.choose"/></option>
                    <#if categories??>
                    <#list categories as cat>
                    <option value="${cat.id}" <#if resume.categoryId?? && resume.categoryId == cat.id>selected</#if>>${cat.name}</option>
                </#list>
            </#if>
            </select>
        </div>

            <div class="mb-3">
                <label for="applicantName" class="form-label"><@spring.message "resumeForm.applicantName"/></label>
                <input type="text" class="form-control" id="applicantName" name="applicantName" value="${(resume.applicantName)!''}">
            </div>

            <div class="mb-3">
                <label for="expectedSalary" class="form-label"><@spring.message "resumeForm.salary"/></label>
                <input type="number" step="0.01" class="form-control" id="expectedSalary" name="expectedSalary" value="${(resume.expectedSalary?c)!''}" required>
            </div>

            <div class="mb-3">
                <label for="skills" class="form-label"><@spring.message "resumeForm.skills"/></label>
                <textarea class="form-control" id="skills" name="skills" rows="3">${(resume.skills)!''}</textarea>
            </div>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header fw-bold"><@spring.message "resumeForm.section.contacts"/></div>
        <div class="card-body">
            <div class="row">
                <#list contactTypes as ct>
                <div class="col-md-3 mb-3">
                    <label for="contactInfos${ct_index}.value" class="form-label">${contactTypeLabel(ct.typeName)}</label>
                    <input type="hidden" name="contactInfos[${ct_index}].contactTypeId" value="${ct.id?c}">
                    <input type="text" class="form-control" id="contactInfos${ct_index}.value" name="contactInfos[${ct_index}].value" value="${getContactValue(resume.contactInfos, ct.id)}">
                </div>
                </#list>
            </div>
        </div>
    </div>

    <div class="card mb-4">
        <div class="card-header fw-bold">
            <span><@spring.message "resumeForm.section.experience"/></span>
        </div>
        <div class="card-body" id="experienceContainer">
            <#if resume.experiences?? && resume.experiences?size gt 0>
            <#list resume.experiences as exp>
            <div class="experience-item border p-3 mb-3 rounded">
                <div class="mb-2">
                    <label class="form-label"><@spring.message "resumeForm.exp.company"/></label>
                    <input type="text" class="form-control" name="experiences[${exp_index}].companyName" value="${(exp.companyName)!''}" required>
                </div>
                <div class="mb-2">
                    <label class="form-label"><@spring.message "resumeForm.exp.position"/></label>
                    <input type="text" class="form-control" name="experiences[${exp_index}].position" value="${(exp.position)!''}" required>
                </div>
                <div class="mb-2">
                    <label class="form-label"><@spring.message "resumeForm.exp.period"/></label>
                    <input type="text" class="form-control" name="experiences[${exp_index}].period" value="${(exp.period)!''}">
                </div>
                <div class="mb-2">
                    <label class="form-label"><@spring.message "resumeForm.exp.responsibilities"/></label>
                    <textarea class="form-control" name="experiences[${exp_index}].responsibilities" rows="2">${(exp.responsibilities)!''}</textarea>
                </div>
            </div>
        </#list>
    </#if>
</div>
</div>

<div class="card mb-4">
    <div class="card-header fw-bold">
        <span><@spring.message "resumeForm.section.education"/></span>
    </div>
    <div class="card-body" id="educationContainer">
        <#if resume.educations?? && resume.educations?size gt 0>
        <#list resume.educations as edu>
        <div class="education-item border p-3 mb-3 rounded">
            <div class="mb-2">
                <label class="form-label"><@spring.message "resumeForm.edu.institution"/></label>
                <input type="text" class="form-control" name="educations[${edu_index}].institution" value="${(edu.institution)!''}">
            </div>
            <div class="mb-2">
                <label class="form-label"><@spring.message "resumeForm.edu.faculty"/></label>
                <input type="text" class="form-control" name="educations[${edu_index}].faculty" value="${(edu.faculty)!''}">
            </div>
            <div class="mb-2">
                <label class="form-label"><@spring.message "resumeForm.edu.enrollmentYear"/></label>
                <input type="number" class="form-control" name="educations[${edu_index}].enrollmentYear" value="${(edu.enrollmentYear?c)!''}">
            </div>
            <div class="mb-2">
                <label class="form-label"><@spring.message "resumeForm.edu.graduationYear"/></label>
                <input type="number" class="form-control" name="educations[${edu_index}].graduationYear" value="${(edu.graduationYear?c)!''}">
            </div>
        </div>
    </#list>
</#if>
</div>
</div>

<div class="d-flex gap-2 mb-5">
    <button type="submit" class="btn btn-primary btn-lg"><@spring.message "resumeForm.submit.edit"/></button>
    <a href="/vacancies" class="btn btn-outline-secondary btn-lg"><@spring.message "common.cancel"/></a>
</div>
</form>
</div>
</@main.mainLayout>

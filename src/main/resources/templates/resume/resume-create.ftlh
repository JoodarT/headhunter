<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#function contactTypeLabel typeName>
    <#return springMacroRequestContext.getMessage("contactType." + typeName, typeName)>
</#function>
<@main.mainLayout title=springMacroRequestContext.getMessage('page.resumeCreate.title')>
<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-9">
            <h2 class="mb-4"><@spring.message "resumeCreate.heading"/></h2>

            <#if error??>
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </#if>

        <form action="/resumes/create" method="post" id="resumeForm">
                <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </#if>

            <div class="card mb-4 shadow-sm">
                <div class="card-header bg-light fw-bold"><@spring.message "resumeForm.section.main"/></div>
                <div class="card-body">
                    <div class="mb-3">
                        <label for="title" class="form-label"><@spring.message "resumeForm.title"/></label>
                        <input type="text" class="form-control" id="title" name="title" required placeholder="<@spring.message "resumeForm.title.placeholder"/>">
                    </div>

                    <div class="mb-3">
                        <label for="categoryId" class="form-label"><@spring.message "resumeForm.category"/></label>
                        <select class="form-select" id="categoryId" name="categoryId" required>
                            <option value="" disabled selected><@spring.message "resumeForm.category.choose"/></option>
                            <#if categories??>
                            <#list categories as cat>
                            <option value="${cat.id}">${cat.name}</option>
                        </#list>
                    </#if>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="applicantName" class="form-label"><@spring.message "resumeForm.applicantName"/></label>
                    <input type="text" class="form-control" id="applicantName" name="applicantName" placeholder="<@spring.message "resumeForm.applicantName.placeholder"/>">
                </div>

                <div class="mb-3">
                    <label for="expectedSalary" class="form-label"><@spring.message "resumeForm.salary"/></label>
                    <input type="number" step="0.01" class="form-control" id="expectedSalary" name="expectedSalary" required placeholder="100000">
                </div>

                <div class="mb-3">
                    <label for="skills" class="form-label"><@spring.message "resumeForm.skills"/></label>
                    <textarea class="form-control" id="skills" name="skills" rows="3" placeholder="<@spring.message "resumeForm.skills.placeholder"/>"></textarea>
                </div>
            </div>
        </div>

        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-light fw-bold"><@spring.message "resumeForm.section.contacts"/></div>
            <div class="card-body">
                <div class="row">
                    <#list contactTypes as ct>
                    <div class="col-md-3 mb-3">
                        <label for="contactInfos${ct_index}.value" class="form-label">${contactTypeLabel(ct.typeName)}</label>
                        <input type="hidden" name="contactInfos[${ct_index}].contactTypeId" value="${ct.id?c}">
                        <input type="text" class="form-control" id="contactInfos${ct_index}.value" name="contactInfos[${ct_index}].value">
                    </div>
                    </#list>
                </div>
            </div>
        </div>

        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-light fw-bold">
                <span><@spring.message "resumeForm.section.experience"/></span>
            </div>
            <div class="card-body" id="experienceContainer">
                <div class="experience-item border p-3 mb-3 rounded position-relative" id="exp-0">
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.exp.company"/></label>
                        <input type="text" class="form-control" name="experiences[0].companyName" required placeholder="<@spring.message "resumeForm.exp.company.placeholder"/>">
                    </div>
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.exp.position"/></label>
                        <input type="text" class="form-control" name="experiences[0].position" required placeholder="<@spring.message "resumeForm.exp.position.placeholder"/>">
                    </div>
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.exp.period"/></label>
                        <input type="text" class="form-control" name="experiences[0].period" placeholder="<@spring.message "resumeForm.exp.period.placeholder"/>">
                    </div>
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.exp.responsibilities"/></label>
                        <textarea class="form-control" name="experiences[0].responsibilities" rows="2" placeholder="<@spring.message "resumeForm.exp.responsibilities.placeholder"/>"></textarea>
                    </div>
                </div>
            </div>
        </div>

        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-light fw-bold">
                <span><@spring.message "resumeForm.section.education"/></span>
            </div>
            <div class="card-body" id="educationContainer">
                <div class="education-item border p-3 mb-3 rounded position-relative" id="edu-0">
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.edu.institution"/></label>
                        <input type="text" class="form-control" name="educations[0].institution" placeholder="<@spring.message "resumeForm.edu.institution.placeholder"/>">
                    </div>
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.edu.faculty"/></label>
                        <input type="text" class="form-control" name="educations[0].faculty" placeholder="<@spring.message "resumeForm.edu.faculty.placeholder"/>">
                    </div>
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.edu.enrollmentYear"/></label>
                        <input type="number" class="form-control" name="educations[0].enrollmentYear" placeholder="2019">
                    </div>
                    <div class="mb-2">
                        <label class="form-label"><@spring.message "resumeForm.edu.graduationYear"/></label>
                        <input type="number" class="form-control" name="educations[0].graduationYear" placeholder="2023">
                    </div>

                </div>
            </div>
        </div>

        <div class="d-flex gap-3 mb-5">
            <button type="submit" class="btn btn-primary btn-lg"><@spring.message "resumeForm.submit.create"/></button>
            <a href="/vacancies" class="btn btn-outline-secondary btn-lg"><@spring.message "common.cancel"/></a>
        </div>
        </form>
    </div>
</div>
</div>

</@main.mainLayout>

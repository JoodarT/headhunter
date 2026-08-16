<#import "layout.ftlh" as main>
<@main.mainLayout title="Редактирование резюме">
<div class="container mt-4">
    <h2>Редактирование резюме</h2>

    <#if error??>
    <div class="alert alert-danger" role="alert">
        ${error}
    </div>
</#if>

<form action="/resumes/${resume.id}/edit" method="post" id="resumeForm">
        <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </#if>

    <#-- Основная информация -->
    <div class="card mb-4">
        <div class="card-header fw-bold">Основная информация</div>
        <div class="card-body">
            <div class="mb-3">
                <label for="title" class="form-label">Название должности / резюме*</label>
                <input type="text" class="form-control" id="title" name="title" value="${(resume.title)!''}" required>
            </div>

            <div class="mb-3">
                <label for="categoryId" class="form-label">Категория / Сфера деятельности*</label>
                <select class="form-select" id="categoryId" name="categoryId" required>
                    <option value="" disabled <#if !resume.categoryId??>selected</#if>>Выберите категорию...</option>
                    <#if categories??>
                    <#list categories as cat>
                    <option value="${cat.id}" <#if resume.categoryId?? && resume.categoryId == cat.id>selected</#if>>${cat.name}</option>
                </#list>
            </#if>
            </select>
        </div>

            <div class="mb-3">
                <label for="applicantName" class="form-label">Имя и Фамилия соискателя</label>
                <input type="text" class="form-control" id="applicantName" name="applicantName" value="${(resume.applicantName)!''}">
            </div>

            <div class="mb-3">
                <label for="expectedSalary" class="form-label">Желаемая зарплата*</label>
                <input type="number" step="0.01" class="form-control" id="expectedSalary" name="expectedSalary" value="${(resume.expectedSalary?c)!''}" required>
            </div>

            <div class="mb-3">
                <label for="skills" class="form-label">Ключевые навыки</label>
                <textarea class="form-control" id="skills" name="skills" rows="3">${(resume.skills)!''}</textarea>
            </div>
        </div>
    </div>

    <#-- Контактная информация -->
    <div class="card mb-4">
        <div class="card-header fw-bold">Контактная информация</div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.phone" class="form-label">Телефон</label>
                    <input type="text" class="form-control" id="contactInfo.phone" name="contactInfo.phone" value="${(resume.contactInfo.phone)!''}">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="contactInfo.email" name="contactInfo.email" value="${(resume.contactInfo.email)!''}">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.telegram" class="form-label">Telegram</label>
                    <input type="text" class="form-control" id="contactInfo.telegram" name="contactInfo.telegram" value="${(resume.contactInfo.telegram)!''}">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.linkedin" class="form-label">LinkedIn</label>
                    <input type="text" class="form-control" id="contactInfo.linkedin" name="contactInfo.linkedin" value="${(resume.contactInfo.linkedin)!''}">
                </div>
            </div>
        </div>
    </div>

    <#-- Опыт работы -->
    <div class="card mb-4">
        <div class="card-header d-flex justify-content-between align-items-center fw-bold">
            <span>Опыт работы</span>
            <button type="button" class="btn btn-sm btn-outline-primary" onclick="addExperience()">+ Добавить место работы</button>
        </div>
        <div class="card-body" id="experienceContainer">
            <#if resume.experiences?? && resume.experiences?size gt 0>
            <#list resume.experiences as exp>
            <div class="experience-item border p-3 mb-3 rounded">
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn-close" onclick="this.parentElement.parentElement.remove()"></button>
                </div>
                <div class="mb-2">
                    <label class="form-label">Компания / Организация*</label>
                    <input type="text" class="form-control" name="experiences[${exp_index}].companyName" value="${(exp.companyName)!''}" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Должность*</label>
                    <input type="text" class="form-control" name="experiences[${exp_index}].position" value="${(exp.position)!''}" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Период работы</label>
                    <input type="text" class="form-control" name="experiences[${exp_index}].period" value="${(exp.period)!''}">
                </div>
                <div class="mb-2">
                    <label class="form-label">Обязанности и достижения</label>
                    <textarea class="form-control" name="experiences[${exp_index}].responsibilities" rows="2">${(exp.responsibilities)!''}</textarea>
                </div>
            </div>
        </#list>
    </#if>
</div>
</div>

<#-- Образование -->
<div class="card mb-4">
    <div class="card-header d-flex justify-content-between align-items-center fw-bold">
        <span>Образование</span>
        <button type="button" class="btn btn-sm btn-outline-primary" onclick="addEducation()">+ Добавить место учебы</button>
    </div>
    <div class="card-body" id="educationContainer">
        <#if resume.educations?? && resume.educations?size gt 0>
        <#list resume.educations as edu>
        <div class="education-item border p-3 mb-3 rounded">
            <div class="d-flex justify-content-end">
                <button type="button" class="btn-close" onclick="this.parentElement.parentElement.remove()"></button>
            </div>
            <div class="mb-2">
                <label class="form-label">Учебное заведение</label>
                <input type="text" class="form-control" name="educations[${edu_index}].institution" value="${(edu.institution)!''}">
            </div>
            <div class="mb-2">
                <label class="form-label">Специальность / Факультет</label>
                <input type="text" class="form-control" name="educations[${edu_index}].faculty" value="${(edu.faculty)!''}">
            </div>
            <div class="mb-2">
                <label class="form-label">Год окончания</label>
                <input type="number" class="form-control" name="educations[${edu_index}].graduationYear" value="${(edu.graduationYear?c)!''}">
            </div>
        </div>
    </#list>
</#if>
</div>
</div>

<div class="d-flex gap-2 mb-5">
    <button type="submit" class="btn btn-primary btn-lg">Сохранить изменения</button>
    <a href="/resumes" class="btn btn-outline-secondary btn-lg">Отмена</a>
</div>
</form>
</div>

<script>
    let expCount = ${(resume.experiences??)?string(resume.experiences?size, 0)};
    function addExperience() {
        const container = document.getElementById('experienceContainer');
        const html = `
            <div class="experience-item border p-3 mb-3 rounded">
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn-close" onclick="this.parentElement.parentElement.remove()"></button>
                </div>
                <div class="mb-2">
                    <label class="form-label">Компания / Организация*</label>
                    <input type="text" class="form-control" name="experiences[` + expCount + `].companyName" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Должность*</label>
                    <input type="text" class="form-control" name="experiences[` + expCount + `].position" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Период работы</label>
                    <input type="text" class="form-control" name="experiences[` + expCount + `].period">
                </div>
                <div class="mb-2">
                    <label class="form-label">Обязанности и достижения</label>
                    <textarea class="form-control" name="experiences[` + expCount + `].responsibilities" rows="2"></textarea>
                </div>
            </div>`;
        container.insertAdjacentHTML('beforeend', html);
        expCount++;
    }

    let eduCount = ${(resume.educations??)?string(resume.educations?size, 0)};
    function addEducation() {
        const container = document.getElementById('educationContainer');
        const html = `
            <div class="education-item border p-3 mb-3 rounded">
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn-close" onclick="this.parentElement.parentElement.remove()"></button>
                </div>
                <div class="mb-2">
                    <label class="form-label">Учебное заведение</label>
                    <input type="text" class="form-control" name="educations[` + eduCount + `].institution">
                </div>
                <div class="mb-2">
                    <label class="form-label">Специальность / Факультет</label>
                    <input type="text" class="form-control" name="educations[` + eduCount + `].faculty">
                </div>
                <div class="mb-2">
                    <label class="form-label">Год окончания</label>
                    <input type="number" class="form-control" name="educations[` + eduCount + `].graduationYear">
                </div>
            </div>`;
        container.insertAdjacentHTML('beforeend', html);
        eduCount++;
    }
</script>
</@main.mainLayout>
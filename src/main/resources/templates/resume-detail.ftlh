<#import "layout.ftlh" as layout>
<@layout.mainLayout title="${resume.title!'Резюме'}">
<div class="row justify-content-center">
    <div class="col-md-9 bg-white p-4 rounded shadow-sm">

        <!-- Шапка резюме -->
        <div class="d-flex justify-content-between align-items-start mb-4 border-bottom pb-3">
            <div>
                <h2 class="mb-1 text-primary">${resume.title!"Заголовок не указан"}</h2>
                <#if resume.applicantName??>
                <h5 class="text-secondary mb-2">${resume.applicantName}</h5>
            </#if>
            <div class="d-flex align-items-center gap-3">
                    <span class="fs-4 fw-bold text-success">
                        <#if resume.expectedSalary??>
                            ${resume.expectedSalary} KGS
                        <#else>
                            Зарплата не указана
                        </#if>
                </span>
                <#if resume.createdDate??>
                <small class="text-muted">Опубликовано: ${resume.createdDate}</small>
            </#if>
        </div>
    </div>

    <!-- Кнопки управления видны только владельцу -->
    <#if isOwner?? && isOwner>
    <div class="d-flex gap-2">
        <a href="/resumes/${resume.id}/edit" class="btn btn-outline-primary">
            <i class="bi bi-pencil"></i> Редактировать
        </a>

        <form action="/resumes/${resume.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Вы действительно хотите удалить резюме?');">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>
        <button type="submit" class="btn btn-outline-danger">Удалить</button>
        </form>
    </div>
</#if>
</div>

<!-- Категория -->
<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6">Специализация / Категория</h5>
    <p class="lead fw-normal">${resume.category!"Не указана"}</p>
</div>

<!-- Навыки -->
<#if resume.skills?? && resume.skills?has_content>
<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6">Ключевые навыки</h5>
    <div class="p-3 bg-light rounded border">
        <p class="mb-0" style="white-space: pre-line;">${resume.skills}</p>
    </div>
</div>
</#if>

<!-- Опыт работы -->
<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6 mb-3">Опыт работы</h5>
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
<p class="text-muted italic">Опыт работы не указан</p>
</#if>
</div>

<!-- Образование -->
<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6 mb-3">Образование</h5>
    <#if resume.educations?? && (resume.educations?size > 0)>
    <#list resume.educations as edu>
    <div class="card mb-2 border-0 bg-light p-3">
        <h5 class="mb-1">${edu.institution!}</h5>
        <p class="mb-0 text-secondary">
            ${edu.faculty!}
            <#if edu.graduationYear??>
            <strong class="ms-1">(${edu.graduationYear} г.)</strong>
        </#if>
        </p>
    </div>
</#list>
<#else>
<p class="text-muted italic">Образование не указано</p>
</#if>
</div>

<!-- Контакты -->
<div class="mb-4">
    <h5 class="text-muted text-uppercase fs-6 mb-3">Контакты</h5>
    <#if resume.contactInfo??>
    <div class="row g-2">
        <#if resume.contactInfo.email?? && resume.contactInfo.email?has_content>
        <div class="col-md-6">
            <strong>Email:</strong> <a href="mailto:${resume.contactInfo.email}">${resume.contactInfo.email}</a>
        </div>
    </#if>
    <#if resume.contactInfo.phone?? && resume.contactInfo.phone?has_content>
    <div class="col-md-6">
        <strong>Телефон:</strong> ${resume.contactInfo.phone}
    </div>
</#if>
<#if resume.contactInfo.telegram?? && resume.contactInfo.telegram?has_content>
<div class="col-md-6">
    <strong>Telegram:</strong> ${resume.contactInfo.telegram}
</div>
</#if>
<#if resume.contactInfo.linkedin?? && resume.contactInfo.linkedin?has_content>
<div class="col-md-6">
    <strong>LinkedIn:</strong> ${resume.contactInfo.linkedin}
</div>
</#if>
</div>
<#else>
<p class="text-muted italic">Контакты не указаны</p>
</#if>
</div>

<!-- Кнопка назад -->
<div class="mt-4 pt-3 border-top">
    <a href="/resumes" class="btn btn-outline-secondary">← Назад к списку резюме</a>
</div>

</div>
</div>
</@layout.mainLayout>
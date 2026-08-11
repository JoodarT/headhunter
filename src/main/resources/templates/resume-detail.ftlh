<#import "layout.ftlh" as layout>
<@layout.mainLayout title="${resume.title!'Резюме'}">
<div class="row justify-content-center">
    <div class="col-md-8 bg-white p-4 rounded shadow-sm">

        <!-- Шапка резюме -->
        <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
            <div>
                <h2 class="mb-1">${resume.title!"Заголовок не указан"}</h2>
                <p class="text-muted mb-0">Желаемая зарплата: <strong>${resume.expectedSalary!"Не указана"} KGS</strong></p>
            </div>

            <!-- Кнопки управление видны только владельцу -->
            <#if isOwner?? && isOwner>
            <div class="d-flex gap-2">
                <a href="/resumes/${resume.id}/edit" class="btn btn-primary">Редактировать</a>

                <form action="/resumes/${resume.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Вы действительно хотите удалить резюме?');">
                    <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>
                <button type="submit" class="btn btn-danger">Удалить</button>
                </form>
            </div>
        </#if>
    </div>

    <!-- Основная информация -->
    <div class="mb-4">
        <h4>Категория / Специализация</h4>
        <p class="lead">${resume.category!"Не указана"}</p>
    </div>

    <div class="mb-4">
        <h4>Навыки</h4>
        <p style="white-space: pre-line;">${resume.skills!"Не указаны"}</p>
    </div>

    <!-- Опыт работы -->
    <div class="mb-4">
        <h4>Опыт работы</h4>
        <#if resume.experiences?? && (resume.experiences?size > 0)>
        <#list resume.experiences as exp>
        <div class="card mb-2 p-3 bg-light border-0">
            <h5 class="mb-1">${exp.position!} — ${exp.companyName!}</h5>
            <small class="text-muted">${exp.period!}</small>
            <p class="mt-2 mb-0">${exp.responsibilities!}</p>
        </div>
    </#list>
    <#else>
    <p class="text-muted">Опыт работы не указан</p>
</#if>
</div>

<!-- Образование -->
<div class="mb-4">
    <h4>Образование</h4>
    <#if resume.educations?? && (resume.educations?size > 0)>
    <#list resume.educations as edu>
    <div class="card mb-2 p-3 bg-light border-0">
        <h5 class="mb-1">${edu.institution!}</h5>
        <p class="mb-0">${edu.faculty!} (${edu.graduationYear!})</p>
    </div>
</#list>
<#else>
<p class="text-muted">Образование не указано</p>
</#if>
</div>

<!-- Контакты -->
<div class="mb-4">
    <h4>Контакты</h4>
    <#if resume.contactInfo??>
    <p class="mb-1"><strong>Email:</strong> ${resume.contactInfo.email!"Не указан"}</p>
    <p class="mb-1"><strong>Телефон:</strong> ${resume.contactInfo.phone!"Не указан"}</p>
    <#if resume.contactInfo.telegram??><p class="mb-1"><strong>Telegram:</strong> ${resume.contactInfo.telegram}</p></#if>
<#if resume.contactInfo.linkedin??><p class="mb-1"><strong>LinkedIn:</strong> ${resume.contactInfo.linkedin}</p></#if>
<#else>
<p class="text-muted">Контакты не указаны</p>
</#if>
</div>

<div class="mt-4 pt-3 border-top">
    <a href="/resumes" class="btn btn-outline-secondary">← Назад к списку резюме</a>
</div>

</div>
</div>
</@layout.mainLayout>
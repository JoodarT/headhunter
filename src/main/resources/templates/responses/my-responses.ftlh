<#import "../layout.ftlh" as layout>
<@layout.mainLayout title="Отклики">
<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0"><#if isEmployer>Отклики на мои вакансии<#else>Мои отклики</#if></h2>
            <a href="/profile" class="btn btn-outline-secondary btn-sm">Назад в кабинет</a>
        </div>

        <#if responses?? && responses?has_content>
        <#list responses as response>
        <div class="card mt-3">
            <div class="card-body d-flex justify-content-between align-items-start">
                <div>
                    <a href="/vacancies/${response.vacancyId}" class="text-decoration-none fw-semibold">${response.vacancyTitle!""}</a>
                    <div class="text-muted small mt-1">${response.company!"Компания не указана"}</div>
                    <#if isEmployer>
                    <div class="small mt-1">
                        <strong>Соискатель:</strong> ${response.applicantName!"Не указан"}
                        &middot;
                        <a href="/resumes/${response.resumeId}" class="text-decoration-none">${response.resumeTitle!""}</a>
                    </div>
                    <#else>
                    <div class="small mt-1">
                        <strong>Резюме:</strong> <a href="/resumes/${response.resumeId}" class="text-decoration-none">${response.resumeTitle!""}</a>
                    </div>
                </#if>
                </div>
                <#if response.confirmation?? && response.confirmation>
                <span class="badge bg-success">Подтверждено</span>
                <#else>
                <span class="badge bg-secondary">На рассмотрении</span>
            </#if>
            </div>
        </div>
        </#list>
        <#else>
        <p class="text-muted">
            <#if isEmployer>На ваши вакансии пока никто не откликнулся.<#else>Вы пока не откликнулись ни на одну вакансию.</#if>
        </p>
    </#if>
    </div>
</div>
</@layout.mainLayout>

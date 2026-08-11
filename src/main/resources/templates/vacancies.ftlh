<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Список вакансий">
<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>Список вакансий</h2>
    <a href="/vacancies/create" class="btn btn-success">+ Создать вакансию</a>
</div>

<div class="row">
    <#if vacancies?? && (vacancies?size > 0)>
    <#list vacancies as vacancy>
    <div class="col-md-6 mb-3">
        <div class="card shadow-sm h-100">
            <div class="card-body d-flex flex-column justify-content-between">
                <div>
                    <h5 class="card-title text-success">${vacancy.title!""}</h5>
                    <p class="card-text text-muted mb-2">
                        <small>Категория: ${vacancy.category!"Не указана"}</small>
                    </p>
                    <p class="card-text">${vacancy.description!""}</p>
                </div>
                <div class="mt-3 pt-2 border-top d-flex justify-content-between align-items-center">
                            <span class="text-primary font-weight-bold">
                                <strong>${vacancy.salary!0} KGS</strong>
                            </span>
                    <a href="/vacancies/${vacancy.id}" class="btn btn-outline-primary btn-sm">Подробнее</a>
                </div>
            </div>
        </div>
    </div>
</#list>
<#else>
<div class="col-12">
    <div class="alert alert-info">Список вакансий пока пуст.</div>
</div>
</#if>
</div>
</@layout.mainLayout>
<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Список вакансий">
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Список вакансий</h2>
        <a href="/vacancies/create" class="btn btn-success">+ Создать вакансию</a>
    </div>

    <div class="row">
        <#if vacancies?? && (vacancies?size > 0)>
        <#list vacancies as vacancy>
        <div class="col-md-6 mb-4">
            <div class="card shadow-sm h-100 border-0">
                <div class="card-body d-flex flex-column justify-content-between">
                    <div>
                        <h5 class="card-title text-primary mb-2">
                            <a href="/vacancies/${vacancy.id}" class="text-decoration-none">${vacancy.title!""}</a>
                        </h5>
                        <p class="card-text text-muted mb-2">
                            <small>
                                <#if vacancy.categoryName??>
                                Категория: ${vacancy.categoryName}
                                <#elseif vacancy.categoryId??>
                                Категория ID: ${vacancy.categoryId}
                                <#else>
                                Категория: Не указана
                            </#if>
                            </small>
                        </p>
                        <p class="card-text text-secondary" style="display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden;">
                            ${vacancy.description!""}
                        </p>
                    </div>

                    <div class="mt-3 pt-2 border-top d-flex justify-content-between align-items-center">
                                <span class="text-success fw-bold fs-5">
                                    <#if vacancy.salary??>
                                        ${vacancy.salary} KGS
                                    <#else>
                                        Договорная
                                    </#if>
                        </span>
                        <a href="/vacancies/${vacancy.id}" class="btn btn-outline-primary btn-sm">Подробнее</a>
                    </div>
                </div>
            </div>
        </div>
    </#list>
    <#else>
    <div class="col-12">
        <div class="alert alert-info text-center py-4">
            Список вакансий пока пуст. Будьте первыми, кто создаст вакансию!
        </div>
    </div>
</#if>
</div>
</div>
</@layout.mainLayout>
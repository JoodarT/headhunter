<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Список вакансий">
<h2 class="mb-4">Список вакансий</h2>
<div class="row">
    <#if vacancies?? && (vacancies?size > 0)>
    <#list vacancies as vacancy>
    <div class="col-md-6 mb-3">
        <div class="card shadow-sm h-100">
            <div class="card-body">
                <h5 class="card-title text-success">${vacancy.title!""}</h5>
                <p class="card-text">${vacancy.description!""}</p>
                <p class="card-text"><strong>Категория:</strong> ${vacancy.category!""}</p>
                <p class="card-text text-primary font-weight-bold">
                    <strong>Оклад:</strong> ${vacancy.salary!0} $
                </p>
            </div>
        </div>
    </div>
</#list>
<#else>
<div class="alert alert-info">Список вакансий пока пуст.</div>
</#if>
</div>
</@layout.mainLayout>
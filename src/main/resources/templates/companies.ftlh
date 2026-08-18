<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Компании">
<div class="mt-4">
    <h2 class="mb-4">Компании</h2>

    <div class="row">
        <#if companies?? && (companies?size > 0)>
        <#list companies as c>
        <div class="col-md-4 mb-3">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-1">${c.name}</h5>
                        <p class="card-text text-muted mb-0">
                            Вакансий: ${c.vacancyCount}
                        </p>
                    </div>
                    <a href="/vacancies?company=${c.name?url('UTF-8')}" class="btn btn-outline-primary btn-sm">
                        Смотреть
                    </a>
                </div>
            </div>
        </div>
        </#list>
        <#else>
        <div class="col-12">
            <div class="alert alert-info text-center py-4">
                Компании пока не добавили вакансии.
            </div>
        </div>
    </#if>
    </div>
</div>
</@layout.mainLayout>

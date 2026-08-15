<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Редактирование вакансии">
<div class="row justify-content-center mt-4">
    <div class="col-md-8">
        <h2 class="mb-4">Редактирование вакансии</h2>

        <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </#if>

    <form action="/vacancies/${vacancyId}/edit" method="post">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>

        <div class="card mb-4 shadow-sm">
            <div class="card-body">
                <div class="mb-3">
                    <label for="title" class="form-label">Название вакансии*</label>
                    <input type="text" class="form-control" id="title" name="title" value="${vacancyDto.title!''}" required>
                </div>

                <div class="mb-3">
                    <label for="categoryId" class="form-label">Категория*</label>
                    <select class="form-select" id="categoryId" name="categoryId" required>
                        <#if categories??>
                        <#list categories as category>
                        <option value="${category.id}" <#if vacancyDto.categoryId?? && vacancyDto.categoryId == category.id>selected</#if>>
                    ${category.name}
                    </option>
                </#list>
            </#if>
            </select>
        </div>

        <div class="mb-3">
            <label for="salary" class="form-label">Зарплата (KGS)*</label>
            <input type="number" step="0.01" class="form-control" id="salary" name="salary" value="${(vacancyDto.salary!0)?c}" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Описание вакансии*</label>
            <textarea class="form-control" id="description" name="description" rows="6" required>${vacancyDto.description!''}</textarea>
        </div>
    </div>
</div>

<div class="d-flex gap-2">
    <button type="submit" class="btn btn-primary">Сохранить изменения</button>
    <a href="/vacancies/${vacancyId}" class="btn btn-outline-secondary">Отмена</a>
</div>
</form>
</div>
</div>
</@layout.mainLayout>
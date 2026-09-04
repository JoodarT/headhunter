<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.mainLayout title=springMacroRequestContext.getMessage('page.vacancyEdit.title')>
<div class="row justify-content-center mt-4">
    <div class="col-md-8">
        <h2 class="mb-4"><@spring.message "vacancyEdit.heading"/></h2>

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
                    <label for="title" class="form-label"><@spring.message "vacancyForm.title"/></label>
                    <input type="text" class="form-control" id="title" name="title" value="${vacancyDto.title!''}" required>
                </div>

                <div class="mb-3">
                    <label for="company" class="form-label"><@spring.message "vacancyForm.company"/></label>
                    <input type="text" class="form-control" id="company" name="company" value="${vacancyDto.company!''}" required>
                </div>

                <div class="mb-3">
                    <label for="categoryId" class="form-label"><@spring.message "vacancyForm.category"/></label>
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
            <label for="salary" class="form-label"><@spring.message "vacancyForm.salary"/></label>
            <input type="number" step="0.01" class="form-control" id="salary" name="salary" value="${(vacancyDto.salary!0)?c}" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label"><@spring.message "vacancyForm.description"/></label>
            <textarea class="form-control" id="description" name="description" rows="6" required>${vacancyDto.description!''}</textarea>
        </div>
    </div>
</div>

<div class="d-flex gap-2">
    <button type="submit" class="btn btn-primary"><@spring.message "common.saveChanges"/></button>
    <a href="/vacancies/${vacancyId}" class="btn btn-outline-secondary"><@spring.message "common.cancel"/></a>
</div>
</form>
</div>
</div>
</@layout.mainLayout>

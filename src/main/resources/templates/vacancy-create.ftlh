<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Создание вакансии">
<div class="row justify-content-center mt-4">
    <div class="col-md-8">
        <h2 class="mb-4">Создание вакансии</h2>

        <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </#if>

    <form action="/vacancies/create" method="post">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>

        <div class="card mb-4 shadow-sm">
            <div class="card-body">
                <div class="mb-3">
                    <label for="title" class="form-label">Название вакансии*</label>
                    <input type="text" class="form-control" id="title" name="title" required placeholder="Например: Senior Java Developer">
                </div>

                <div class="mb-3">
                    <label for="categoryId" class="form-label">Категория*</label>
                    <select class="form-select" id="categoryId" name="categoryId" required>
                        <option value="" disabled selected>Выберите категорию...</option>
                        <#if categories??>
                        <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </#if>
                </select>
            </div>

            <div class="mb-3">
                <label for="salary" class="form-label">Зарплата (KGS)*</label>
                <input type="number" step="0.01" class="form-control" id="salary" name="salary" required placeholder="100000">
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Описание вакансии*</label>
                <textarea class="form-control" id="description" name="description" rows="6" required placeholder="Опишите требования, условия и задачи..."></textarea>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Название компаний</label>
                <textarea class="form-control" id="company" name="company" rows="6" required placeholder="Название компаний"></textarea>
            </div>
        </div>
    </div>

    <div class="d-flex gap-2">
        <button type="submit" class="btn btn-success">Опубликовать</button>
        <a href="/resumes" class="btn btn-outline-secondary">Отмена</a>
    </div>
    </form>
</div>
</div>
</@layout.mainLayout>
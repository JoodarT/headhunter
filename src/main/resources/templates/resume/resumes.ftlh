<#import "layout.ftlh" as main>
<@main.mainLayout title="Список резюме">
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Список резюме</h2>
        <#if navUser?? && navUser.role?? && navUser.role.role == "APPLICANT">
        <a href="/resumes/create" class="btn btn-success">+ Создать резюме</a>
        <#else>
        <a href="#" class="btn btn-success disabled" aria-disabled="true" tabindex="-1">+ Создать резюме</a>
    </#if>
    </div>

    <#if !(navUser?? && navUser.role?? && navUser.role.role == "APPLICANT")>

</#if>

<#-- поиск -->
    <form action="/resumes" method="get" class="mb-4">
        <div class="input-group">
            <input type="text" name="search" class="form-control" placeholder="Поиск по должности, категории или навыкам..." value="${(search)!''}">
            <button class="btn btn-primary" type="submit">Найти</button>
        </div>
    </form>

    <div class="row">
        <#if resumes?? && resumes?has_content>
        <#list resumes as resume>
        <div class="col-md-6 mb-4">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="/resumes/${resume.id}" class="text-decoration-none">${(resume.title)!""}</a>
                    </h5>
                    <h6 class="card-subtitle mb-2 text-muted">${(resume.applicantName)!"Аноним"}</h6>

                    <p class="card-text mb-1">
                        <strong>Категория:</strong> ${(resume.categoryName)!"Не указана"}
                    </p>

                    <p class="card-text mb-1">
                        <strong>Навыки:</strong> ${(resume.skills)!"Не указаны"}
                    </p>

                    <p class="card-text text-success fw-bold mt-2">
                        <strong>Зарплата:</strong> ${(resume.expectedSalary?string.number)!0} $
                    </p>
                </div>

                <#-- Кнопки управления (Редактирование / Удаление) -->
                <#if currentUser?? && resume.userId?? && resume.userId == currentUser.id>
                <a href="/resumes/${resume.id}/edit" class="btn btn-sm btn-outline-primary">Редактировать</a>

                <form action="/resumes/${resume.id}/delete" method="post" class="d-inline" onsubmit="return confirm('Удалить резюме?');">
                    <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>
                <button type="submit" class="btn btn-sm btn-outline-danger">Удалить</button>
                </form>
            </#if>
            </div>
        </div>
    </#list>
    <#else>
    <div class="col-12">
        <div class="alert alert-info">Список резюме пока пуст.</div>
    </div>
</#if>
</div>
</div>
</@main.mainLayout>
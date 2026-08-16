<#import "layout.ftlh" as layout>
<@layout.mainLayout title="${vacancy.title!'Детали вакансии'}">
<div class="row justify-content-center">
    <div class="col-md-9 bg-white p-4 rounded shadow-sm">

        <!-- Шапка вакансии -->
        <div class="d-flex justify-content-between align-items-start mb-4 border-bottom pb-3">
            <div>
                <h2 class="mb-1 text-primary">${vacancy.title!"Заголовок не указан"}</h2>
                <div class="d-flex align-items-center gap-3 mt-2">
                    <span class="fs-4 fw-bold text-success">
                        <#if vacancy.salary??>
                            ${vacancy.salary} KGS
                        <#else>
                            Зарплата не указана
                        </#if>
                    </span>
                    <span class="badge bg-secondary">
                        👁 Просмотров: ${vacancy.views!0}
                    </span>
                </div>
            </div>

            <!-- Если смотрит работодатель-владелец — показываем кнопки управления -->
            <#if isOwner?? && isOwner>
            <div class="d-flex gap-2">
                <a href="/vacancies/${vacancy.id}/edit" class="btn btn-outline-primary">
                    Редактировать
                </a>

                <form action="/vacancies/${vacancy.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Вы действительно хотите удалить эту вакансию?');">
                    <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>
                <button type="submit" class="btn btn-outline-danger">Удалить</button>
                </form>
            </div>
        </#if>
    </div>

    <!-- Категория -->
    <div class="mb-4">
        <h5 class="text-muted text-uppercase fs-6">Категория / Сфера</h5>
        <p class="lead fw-normal">
            <#if vacancy.categoryName??>
            ${vacancy.categoryName}
            <#elseif vacancy.categoryId??>
            Категория №${vacancy.categoryId}
            <#else>
            Не указана
        </#if>
        </p>
    </div>

    <!-- Описание вакансии -->
    <div class="mb-4">
        <h5 class="text-muted text-uppercase fs-6">Описание вакансии</h5>
        <div class="p-3 bg-light rounded border">
            <p class="mb-0" style="white-space: pre-line;">${vacancy.description!"Описание отсутствует"}</p>
        </div>
    </div>

    <!-- Кнопка назад -->
    <div class="mt-4 pt-3 border-top">
        <a href="/resumes" class="btn btn-outline-secondary">← Назад</a>
    </div>

</div>
</div>
</@layout.mainLayout>
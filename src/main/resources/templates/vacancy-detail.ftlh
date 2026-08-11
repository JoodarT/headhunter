<#import "layout.ftlh" as layout>
<@layout.mainLayout title="${vacancy.title!'Детали вакансии'}">
<div class="row justify-content-center">
    <div class="col-md-8 bg-white p-4 rounded shadow-sm">

        <!-- Шапка вакансии -->
        <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
            <div>
                <h2 class="mb-1">${vacancy.title!"Заголовок не указан"}</h2>
                <p class="text-muted mb-0">Зарплата: <strong>${vacancy.salary!"Не указана"} KGS</strong></p>
                <small class="text-secondary">Просмотров: ${vacancy.views!0}</small>
            </div>

            <!-- Если смотрит работодатель-владелец — показываем кнопки управления -->
            <#if isOwner?? && isOwner>
            <div class="d-flex gap-2">
                <a href="/vacancies/${vacancy.id}/edit" class="btn btn-primary">Редактировать</a>

                <form action="/vacancies/${vacancy.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Вы действительно хотите удалить эту вакансию?');">
                    <#if _csrf??>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </#if>
                <button type="submit" class="btn btn-danger">Удалить</button>
                </form>
            </div>
        </#if>
    </div>

    <!-- Категория -->
    <div class="mb-4">
        <h4>Категория / ID Категории</h4>
        <p class="lead">${vacancy.category!"Не указана"}</p>
    </div>

    <!-- Описание вакансии -->
    <div class="mb-4">
        <h4>Описание вакансии</h4>
        <p style="white-space: pre-line;">${vacancy.description!"Описание отсутствует"}</p>
    </div>

    <!-- Кнопка назад -->
    <div class="mt-4 pt-3 border-top">
        <a href="/vacancies" class="btn btn-outline-secondary">← Назад к списку вакансий</a>
    </div>

</div>
</div>
</@layout.mainLayout>
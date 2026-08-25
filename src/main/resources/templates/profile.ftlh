<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Личный кабинет">
<div class="row justify-content-center">
    <div class="col-md-8 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4">Личный кабинет</h2>
        <#if user??>
        <div class="card mb-3">
            <div class="card-body">
                <div class="row align-items-center">

                    <#-- Блок отображения и загрузки аватарки -->
                    <div class="col-md-4 text-center border-end pe-md-4">
                        <div class="mb-3">
                            <#if user.avatarUrl?? && user.avatarUrl?has_content>
                            <img src="/uploads/avatars/${user.avatarUrl}"                                 alt="Аватар профиля"
                                 class="rounded-circle img-thumbnail shadow-sm"
                                 style="width: 150px; height: 150px; object-fit: cover;">
                            <#else>
                            <#--  аватар -->
                            <img src="/images/default-avatar.png"
                                 alt="Дефолтный аватар"
                                 class="rounded-circle img-thumbnail shadow-sm"
                                 style="width: 150px; height: 150px; object-fit: cover;">
                        </#if>
                    </div>

                    <#-- Форма отправки файла -->
                    <form action="/profile/avatar/${user.id}" method="post" enctype="multipart/form-data">
                        <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </#if>
                    <div class="mb-2">
                        <input type="file" name="file" class="form-control form-control-sm" accept="image/*" required>
                    </div>
                    <button type="submit" class="btn btn-sm btn-outline-primary w-100">Обновить фото</button>
                    </form>
                </div>

                <#-- Блок информации о пользователе -->
                <div class="col-md-8 ps-md-4 mt-3 mt-md-0">
                    <h4 class="card-title mb-3">${user.name!"Имя не указано"}</h4>
                    <p class="card-text mb-2"><strong>Email:</strong> ${user.email!""}</p>
                    <p class="card-text mb-2"><strong>Телефон:</strong> ${user.phone!"Не указан"}</p>
                    <p class="card-text mb-3"><strong>Тип аккаунта:</strong>
                        <span class="badge bg-info text-dark">${(user.role.role)!""}</span>
                    </p>
                    <a href="/profile/edit" class="btn btn-warning">Редактировать профиль</a>
                    <a href="/responses" class="btn btn-outline-primary ms-2">Отклики</a>
                </div>

            </div>
        </div>
    </div>
    <#else>
    <div class="alert alert-warning">Пользователь не найден или вы не авторизованы.</div>
</#if>

    <#if myVacancies??>
    <div class="card mt-3">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0">Мои вакансии (${myVacancies?size})</h4>
                <a href="/vacancies/create" class="btn btn-success btn-sm">+ Создать вакансию</a>
            </div>
            <#if myVacancies?size gt 0>
            <ul class="list-group list-group-flush">
                <#list myVacancies as vacancy>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <a href="/vacancies/${vacancy.id}" class="text-decoration-none">${vacancy.title!""}</a>
                    <span class="text-muted small">Откликов: ${vacancy.responsesCount!0}</span>
                </li>
            </#list>
            </ul>
            <#else>
            <p class="text-muted mb-0">Вы ещё не создали ни одной вакансии.</p>
        </#if>
        </div>
    </div>
</#if>

    <#if myResumes??>
    <div class="d-flex justify-content-between align-items-center mt-4 mb-3">
        <h4 class="mb-0">Мои резюме (${myResumes?size})</h4>
        <a href="/resumes/create" class="btn btn-success btn-sm">+ Создать резюме</a>
    </div>
    <#if myResumes?size gt 0>
    <#list myResumes as resume>
    <#assign resumeUpdatedAt = (resume.updatedDate)!(resume.createdDate)!"">
    <div class="card mt-3">
        <div class="card-body d-flex justify-content-between align-items-start">
            <div>
                <a href="/resumes/${resume.id}" class="text-decoration-none fw-semibold">${resume.title!""}</a>
                <div class="text-muted small mt-1">
                    <span>${resume.categoryName!"Без категории"}</span>
                    <#if resume.skills?? && resume.skills?has_content>
                    <span> &middot; ${resume.skills}</span>
                </#if>
                </div>
                <#if resume.expectedSalary?? && (resume.expectedSalary > 0)>
                <div class="text-success small fw-bold mt-1">${resume.expectedSalary?string.number} $</div>
            </#if>
            </div>
            <span class="text-muted small text-nowrap ms-3">
                Обновлено: <#if resumeUpdatedAt?is_date_like>${resumeUpdatedAt?string("dd.MM.yyyy HH:mm")}<#else>—</#if>
            </span>
        </div>
    </div>
    </#list>
    <#else>
    <p class="text-muted mb-0">Вы ещё не создали ни одного резюме.</p>
</#if>
</#if>
</div>
</div>
</@layout.mainLayout>
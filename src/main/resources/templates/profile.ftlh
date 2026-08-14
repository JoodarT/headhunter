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
                            <#-- Исправлено: путь теперь без /static -->
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
                        <span class="badge bg-info text-dark">${user.accountType!user.role!""}</span>
                    </p>
                    <a href="/profile/edit" class="btn btn-warning">Редактировать профиль</a>
                </div>

            </div>
        </div>
    </div>
    <#else>
    <div class="alert alert-warning">Пользователь не найден или вы не авторизованы.</div>
</#if>
</div>
</div>
</@layout.mainLayout>
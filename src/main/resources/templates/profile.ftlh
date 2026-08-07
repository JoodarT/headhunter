<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Личный кабинет">
<div class="row justify-content-center">
    <div class="col-md-8 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-3">Личный кабинет</h2>
        <#if user??>
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">${user.name!"Имя не указано"}</h5>
                <p class="card-text"><strong>Email:</strong> ${user.email!""}</p>
                <p class="card-text"><strong>Телефон:</strong> ${user.phone!"Не указан"}</p>
                <p class="card-text"><strong>Тип аккаунта:</strong>
                    <span class="badge bg-info text-dark">${user.accountType!""}</span>
                </p>
                <a href="/profile/edit" class="btn btn-warning">Редактировать профиль</a>
            </div>
        </div>
        <#else>
        <div class="alert alert-warning">Пользователь не найден или вы не авторизованы.</div>
    </#if>
</div>
</div>
</@layout.mainLayout>
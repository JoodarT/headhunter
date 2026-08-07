<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Редактирование профиля">
<div class="row justify-content-center">
    <div class="col-md-6 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-3">Редактирование профиля</h2>
        <#if user??>
        <form action="/api/users/${user.id!}" method="post">
            <div class="mb-3">
                <label class="form-label">Имя</label>
                <input type="text" name="name" value="${user.name!""}" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Телефон</label>
                <input type="text" name="phone" value="${user.phone!""}" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">Сохранить изменения</button>
            <a href="/profile" class="btn btn-secondary">Отмена</a>
        </form>
    </#if>
</div>
</div>
</@layout.mainLayout>
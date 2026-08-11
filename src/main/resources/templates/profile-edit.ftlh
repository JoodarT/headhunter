<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Редактирование профиля">
<div class="row justify-content-center">
    <div class="col-md-6 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-3">Редактирование профиля</h2>

        <form action="/profile/edit" method="post">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>

        <#-- Сохраняем accountType, чтобы он не уходил как null в контроллер -->
        <input type="hidden" name="accountType" value="${userDto.accountType!''}"/>

        <div class="mb-3">
            <label class="form-label">Имя</label>
            <input type="text" name="name" value="${userDto.name!''}" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" value="${userDto.email!''}" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Телефон</label>
            <input type="text" name="phone" value="${userDto.phone!''}" class="form-control">
        </div>

        <div class="d-flex justify-content-between mt-4">
            <a href="/profile" class="btn btn-secondary">Отмена</a>
            <button type="submit" class="btn btn-success">Сохранить изменения</button>
        </div>
        </form>
    </div>
</div>
</@layout.mainLayout>
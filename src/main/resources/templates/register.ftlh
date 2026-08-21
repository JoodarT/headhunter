<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Регистрация">
<div class="row justify-content-center">
    <div class="col-md-6 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-3">Регистрация</h2>

        <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </#if>

    <form action="/register" method="post">
        <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>
        <div class="mb-3">
            <label class="form-label">Имя</label>
            <input type="text" name="name" class="form-control" value="${(userDto.name)!''}" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" value="${(userDto.email)!''}" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Пароль</label>
            <input type="password" name="password" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Телефон</label>
            <input type="text" name="phone" class="form-control" value="${(userDto.phone)!''}">
        </div>
        <div class="mb-3">
            <label class="form-label">Тип учетной записи</label>
            <select name="accountType" class="form-select" required>
                <option value="APPLICANT" <#if (userDto.accountType)?? && userDto.accountType == "APPLICANT">selected</#if>>Соискатель</option>
            <option value="EMPLOYER" <#if (userDto.accountType)?? && userDto.accountType == "EMPLOYER">selected</#if>>Работодатель</option>
        </select>
</div>
<button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
</form>
</div>
</div>
</@layout.mainLayout>
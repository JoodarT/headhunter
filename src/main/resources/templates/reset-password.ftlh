<#import "layout.ftlh" as layout>

<@layout.mainLayout title="Новый пароль">
<div class="row justify-content-center">
    <div class="col-md-5 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4 text-center">Новый пароль</h2>

        <#if invalidToken??>
        <div class="alert alert-danger" role="alert">
            Ссылка для восстановления пароля недействительна или её срок истёк. Запросите новую.
        </div>
        <div class="mt-3 text-center">
            <a href="/forgot-password" class="text-decoration-none">Запросить новую ссылку</a>
        </div>
        <#else>

        <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
        </#if>

        <form action="/reset-password" method="post">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </#if>
            <input type="hidden" name="token" value="${token}">
            <div class="mb-3">
                <label class="form-label">Новый пароль</label>
                <input type="password" name="password" class="form-control" required minlength="6">
            </div>
            <div class="mb-3">
                <label class="form-label">Повторите пароль</label>
                <input type="password" name="confirmPassword" class="form-control" required minlength="6">
            </div>
            <button type="submit" class="btn btn-primary w-100">Сохранить пароль</button>
        </form>
        </#if>
    </div>
</div>
</@layout.mainLayout>

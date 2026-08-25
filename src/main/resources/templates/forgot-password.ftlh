<#import "layout.ftlh" as layout>

<@layout.mainLayout title="Восстановление пароля">
<div class="row justify-content-center">
    <div class="col-md-5 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4 text-center">Восстановление пароля</h2>

        <#if submitted??>
        <div class="alert alert-info" role="alert">
            Если указанный email зарегистрирован в системе, для него создана ссылка для сброса пароля.
        </div>

        <#if resetLink??>
        <div class="alert alert-warning" role="alert">
            Почтовый сервис пока не подключен, поэтому ссылка показана прямо здесь (только для разработки):<br>
            <a href="${resetLink}">${resetLink}</a>
        </div>
        </#if>
        </#if>

        <form action="/forgot-password" method="post">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </#if>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required placeholder="name@example.com">
            </div>
            <button type="submit" class="btn btn-primary w-100">Отправить ссылку</button>
        </form>

        <div class="mt-3 text-center">
            <a href="/login" class="text-decoration-none">Вернуться ко входу</a>
        </div>
    </div>
</div>
</@layout.mainLayout>

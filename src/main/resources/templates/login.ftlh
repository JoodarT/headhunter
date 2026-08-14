<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Вход в систему">
<div class="row justify-content-center">
    <div class="col-md-5 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4 text-center">Вход в систему</h2>

        <#if registered??>
        <div class="alert alert-success" role="alert">
            Вы успешно зарегистрировались! Теперь войдите в систему.
        </div>
    </#if>

    <#if error??>
    <div class="alert alert-danger" role="alert">
        Неверный email или пароль.
    </div>
</#if>

<#if logout??>
<div class="alert alert-info" role="alert">
    Вы успешно вышли из системы.
</div>
</#if>

<form action="/login" method="post">
    <div class="mb-3">
        <label class="form-label">Email</label>
        <input type="email" name="username" class="form-control" required placeholder="name@example.com">
    </div>
    <div class="mb-3">
        <label class="form-label">Пароль</label>
        <input type="password" name="password" class="form-control" required>
    </div>
    <button type="submit" class="btn btn-primary w-100">Войти</button>
</form>

<div class="mt-3 text-center">
    <a href="/register" class="text-decoration-none">Зарегистрироваться</a>
</div>
</div>
</div>
</@layout.mainLayout>
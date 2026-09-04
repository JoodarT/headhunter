<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.mainLayout title=springMacroRequestContext.getMessage('page.login.title')>
<div class="row justify-content-center">
    <div class="col-md-5 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4 text-center"><@spring.message "login.heading"/></h2>

        <#if registered??>
        <div class="alert alert-success" role="alert">
            <@spring.message "login.alert.registered"/>
        </div>
    </#if>

    <#if error??>
    <div class="alert alert-danger" role="alert">
        <@spring.message "login.alert.error"/>
    </div>
</#if>

<#if logout??>
<div class="alert alert-info" role="alert">
    <@spring.message "login.alert.logout"/>
</div>
</#if>

<#if passwordReset??>
<div class="alert alert-success" role="alert">
    <@spring.message "login.alert.passwordReset"/>
</div>
</#if>

<form action="/login" method="post">
    <#if _csrf??>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </#if>
    <div class="mb-3">

        <i class="bi bi-envelope-at-fill"></i>
        <label class="form-label"><@spring.message "login.email"/></label>
        <input type="email" name="username" class="form-control" required placeholder="name@example.com">
    </div>
    <div class="mb-3">
        <label class="form-label"><@spring.message "login.password"/></label>
        <input type="password" name="password" class="form-control" required>
    </div>
    <div class="mb-3 text-end">
        <a href="/forgot-password" class="text-decoration-none"><@spring.message "login.forgotPassword"/></a>
    </div>
    <button type="submit" class="btn btn-primary w-100"><@spring.message "login.submit"/></button>
</form>

<div class="mt-3 text-center">
    <a href="/register" class="text-decoration-none"><@spring.message "login.toRegister"/></a>
</div>
</div>
</div>
</@layout.mainLayout>

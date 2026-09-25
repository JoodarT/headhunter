<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.mainLayout title=springMacroRequestContext.getMessage('page.resetPassword.title')>
<div class="row justify-content-center">
    <div class="col-md-5 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4 text-center"><@spring.message "resetPassword.heading"/></h2>

        <#if invalidToken??>
        <div class="alert alert-danger" role="alert">
            <@spring.message "resetPassword.alert.invalidToken"/>
        </div>
        <div class="mt-3 text-center">
            <a href="/forgot-password" class="text-decoration-none"><@spring.message "resetPassword.requestNew"/></a>
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
                <label class="form-label"><@spring.message "resetPassword.newPassword"/></label>
                <input type="password" name="password" class="form-control" required minlength="6">
            </div>
            <div class="mb-3">
                <label class="form-label"><@spring.message "resetPassword.confirmPassword"/></label>
                <input type="password" name="confirmPassword" class="form-control" required minlength="6">
            </div>
            <button type="submit" class="btn btn-primary w-100"><@spring.message "resetPassword.submit"/></button>
        </form>
        </#if>
    </div>
</div>
</@layout.mainLayout>

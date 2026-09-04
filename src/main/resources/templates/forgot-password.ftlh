<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.mainLayout title=springMacroRequestContext.getMessage('page.forgotPassword.title')>
<div class="row justify-content-center">
    <div class="col-md-5 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-4 text-center"><@spring.message "forgotPassword.heading"/></h2>

        <#if submitted??>
        <div class="alert alert-info" role="alert">
            <@spring.message "forgotPassword.alert.submitted"/>
        </div>

        <#if resetLink??>
        <div class="alert alert-warning" role="alert">
            <@spring.message "forgotPassword.alert.devLink"/><br>
            <a href="${resetLink}">${resetLink}</a>
        </div>
        </#if>
        </#if>

        <form action="/forgot-password" method="post">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </#if>
            <div class="mb-3">
                <label class="form-label"><@spring.message "forgotPassword.email"/></label>
                <input type="email" name="email" class="form-control" required placeholder="name@example.com">
            </div>
            <button type="submit" class="btn btn-primary w-100"><@spring.message "forgotPassword.submit"/></button>
        </form>

        <div class="mt-3 text-center">
            <a href="/login" class="text-decoration-none"><@spring.message "forgotPassword.backToLogin"/></a>
        </div>
    </div>
</div>
</@layout.mainLayout>

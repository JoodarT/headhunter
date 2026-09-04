<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.mainLayout title=springMacroRequestContext.getMessage('page.register.title')>
<div class="row justify-content-center">
    <div class="col-md-6 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-3"><@spring.message "register.heading"/></h2>

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
            <label class="form-label"><@spring.message "register.name"/></label>
            <input type="text" name="name" class="form-control" value="${(userDto.name)!''}" required>
        </div>
        <div class="mb-3">
            <label class="form-label"><@spring.message "register.email"/></label>
            <input type="email" name="email" class="form-control" value="${(userDto.email)!''}" required>
        </div>
        <div class="mb-3">
            <label class="form-label"><@spring.message "register.password"/></label>
            <input type="password" name="password" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label"><@spring.message "register.phone"/></label>
            <input type="tel" name="phone" class="form-control" value="${(userDto.phone)!''}" required>
        </div>
        <div class="mb-3">
            <label class="form-label"><@spring.message "register.accountType"/></label>
            <select name="accountType" class="form-select" required>
                <option value="APPLICANT" <#if (userDto.accountType)?? && userDto.accountType == "APPLICANT">selected</#if>><@spring.message "register.accountType.applicant"/></option>
            <option value="EMPLOYER" <#if (userDto.accountType)?? && userDto.accountType == "EMPLOYER">selected</#if>><@spring.message "register.accountType.employer"/></option>
        </select>
</div>
<button type="submit" class="btn btn-primary w-100"><@spring.message "register.submit"/></button>
</form>
</div>
</div>
</@layout.mainLayout>

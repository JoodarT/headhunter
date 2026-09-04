<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.mainLayout title=springMacroRequestContext.getMessage('page.profileEdit.title')>
<div class="row justify-content-center">
    <div class="col-md-6 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-3"><@spring.message "profileEdit.heading"/></h2>

        <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </#if>

    <form action="/profile/edit" method="post">
            <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </#if>

        <input type="hidden" name="accountType" value="${userDto.accountType!''}"/>

        <div class="mb-3">
            <label class="form-label"><@spring.message "profileEdit.name"/></label>
            <input type="text" name="name" value="${userDto.name!''}" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label"><@spring.message "profileEdit.email"/></label>
            <input type="email" name="email" value="${userDto.email!''}" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label"><@spring.message "profileEdit.phone"/></label>
            <input type="text" name="phone" value="${userDto.phone!''}" class="form-control">
        </div>

        <div class="d-flex justify-content-between mt-4">
            <a href="/profile" class="btn btn-secondary"><@spring.message "common.cancel"/></a>
            <button type="submit" class="btn btn-success"><@spring.message "profileEdit.submit"/></button>
        </div>
        </form>
    </div>
</div>
</@layout.mainLayout>

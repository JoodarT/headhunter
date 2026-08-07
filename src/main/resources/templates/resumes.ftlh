<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Список резюме">
<h2 class="mb-4">Список резюме</h2>
<div class="row">
    <#if resumes?? && (resumes?size > 0)>
    <#list resumes as resume>
    <div class="col-md-6 mb-3">
        <div class="card shadow-sm h-100">
            <div class="card-body">
                <h5 class="card-title text-primary">${resume.title!""}</h5>
                <h6 class="card-subtitle mb-2 text-muted">${resume.applicantName!"Аноним"}</h6>
                <p class="card-text"><strong>Категория:</strong> ${resume.category!""}</p>
                <p class="card-text"><strong>Навыки:</strong> ${resume.skills!""}</p>
                <p class="card-text text-success font-weight-bold">
                    <strong>Зарплата:</strong> ${resume.expectedSalary!0} $
                </p>
            </div>
        </div>
    </div>
</#list>
<#else>
<div class="alert alert-info">Список резюме пока пуст.</div>
</#if>
</div>
</@layout.mainLayout>
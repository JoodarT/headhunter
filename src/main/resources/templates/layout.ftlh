<#import "/spring.ftl" as spring>
<#macro mainLayout title="HeadHunter" fluid=false>
<!DOCTYPE html>
<html lang="${springMacroRequestContext.locale.language}" class="h-100">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
</head>
<body class="d-flex flex-column h-100 bg-light">

<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4 shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold fs-4" href="/">HH</a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <#if navUser?? && navUser.role?? && navUser.role.role == "EMPLOYER">
                    <li class="nav-item">
                        <a class="nav-link" href="/resumes"><@spring.message "nav.resumes"/></a>
                    </li>
                    <#elseif navUser?? && navUser.role?? && navUser.role.role == "APPLICANT">
                    <li class="nav-item">
                        <a class="nav-link" href="/vacancies"><@spring.message "nav.vacancies"/></a>
                    </li>
                    <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/resumes"><@spring.message "nav.resumes"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/vacancies"><@spring.message "nav.vacancies"/></a>
                    </li>
                    </#if>
                    <li class="nav-item">
                        <a class="nav-link" href="/companies"><@spring.message "nav.companies"/></a>
                    </li>
                </ul>

                <ul class="navbar-nav ms-auto align-items-lg-center">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-translate"></i>
                            <#if springMacroRequestContext.locale.language == "en">EN<#else>RU</#if>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item<#if springMacroRequestContext.locale.language == "ru"> active</#if>" href="?lang=ru"><@spring.message "lang.ru"/></a></li>
                            <li><a class="dropdown-item<#if springMacroRequestContext.locale.language == "en"> active</#if>" href="?lang=en"><@spring.message "lang.en"/></a></li>
                        </ul>
                    </li>
                    <#if navUser??>
                    <li class="nav-item">
                        <a class="btn btn-outline-light btn-sm ms-2 me-2" href="/profile"><i class="bi bi-person-circle"></i> <@spring.message "nav.profile"/></a>
                    </li>
                    <li class="nav-item">
                        <form action="/logout" method="post" class="d-inline">
                            <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </#if>
                        <button type="submit" class="nav-link btn btn-link p-0 border-0 align-baseline"><@spring.message "nav.logout"/></button>
                        </form>
                    </li>
                    <#else>
                    <li class="nav-item">
                        <a class="nav-link ms-2" href="/login"><@spring.message "nav.login"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-light ms-2" href="/register"><@spring.message "nav.register"/></a>
                    </li>
                </#if>
                </ul>
            </div>
        </div>
    </nav>
</header>

<main class="flex-shrink-0 mb-5">
    <div class="${fluid?string('container-fluid', 'container')}">
        <#nested/>
    </div>
</main>

<footer class="footer mt-auto py-3 bg-white border-top">
    <div class="container text-center text-muted fs-7">
        &copy; ${.now?string('yyyy')} HH App. <@spring.message "footer.rights"/>
    </div>
    <li class="nav-item mt-auto text-muted">
        <a class="nav-link ps-5" href="/resumes"><@spring.message "nav.resumes"/></a>
    </li>

    <li class="nav-item mt-auto text-muted">
        <a class="nav-link ps-5" href="/vacancies"><@spring.message "nav.vacancies"/></a>
    </li>

</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</#macro>

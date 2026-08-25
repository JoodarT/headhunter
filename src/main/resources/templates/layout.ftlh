<#macro mainLayout title="HeadHunter" fluid=false>
<!DOCTYPE html>
<html lang="ru" class="h-100">
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
                        <a class="nav-link" href="/resumes">Резюме</a>
                    </li>
                    <#elseif navUser?? && navUser.role?? && navUser.role.role == "APPLICANT">
                    <li class="nav-item">
                        <a class="nav-link" href="/vacancies">Вакансии</a>
                    </li>
                    <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/resumes">Резюме</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/vacancies">Вакансии</a>
                    </li>
                    </#if>
                    <li class="nav-item">
                        <a class="nav-link" href="/companies">Компании</a>
                    </li>
                </ul>

                <ul class="navbar-nav ms-auto">
                    <#if navUser??>
                    <li class="nav-item">
                        <a class="btn btn-outline-light btn-sm me-2" href="/profile"><i class="bi bi-person-circle"></i> Личный кабинет</a>
                    </li>
                    <li class="nav-item">
                        <form action="/logout" method="post" class="d-inline">
                            <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </#if>
                        <button type="submit" class="nav-link btn btn-link p-0 border-0 align-baseline">Выход</button>
                        </form>
                    </li>
                    <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Вход</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-light ms-2" href="/register">Регистрация</a>
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
        &copy; ${.now?string('yyyy')} HH App. Все права защищены.
    </div>
    <li class="nav-item mt-auto text-muted">
        <a class="nav-link ps-5" href="/resumes">Резюме</a>
    </li>

    <li class="nav-item mt-auto text-muted">
        <a class="nav-link ps-5" href="/vacancies">Вакансии</a>
    </li>

</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</#macro>
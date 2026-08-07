<#macro mainLayout title>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold" href="/resumes">HeadHanter</a>
        <div class="navbar-nav">
            <a class="nav-link" href="/resumes">Резюме</a>
            <a class="nav-link" href="/vacancies">Вакансии</a>
            <a class="nav-link" href="/profile">Личный кабинет</a>
            <a class="nav-link" href="/register">Регистрация</a>
        </div>
    </div>
</nav>

<div class="container">
    <#nested/>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</#macro>
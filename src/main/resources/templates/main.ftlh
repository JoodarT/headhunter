<#import "layout.ftlh" as layout>
<@layout.mainLayout title="HeadHunter">

<#if error??>
<div class="alert alert-danger mt-4" role="alert">
    ${error}
</div>
</#if>

<div id="mainCarousel" class="carousel slide mt-4 mb-5" data-bs-touch="false">
    <div class="carousel-inner rounded shadow-sm">
        <div class="carousel-item active">
            <div class="d-flex flex-column align-items-center justify-content-center text-white text-center p-5"
                 style="background: linear-gradient(rgba(0,0,0,0.35), rgba(0,0,0,0.35)), url('/images/carousel/slide1.png') center/cover no-repeat; min-height: 320px;">
                <h1 class="fw-bold">Найдите работу мечты</h1>
                <p class="fs-5 mb-0">Тысячи вакансий от проверенных работодателей</p>
            </div>
        </div>
        <div class="carousel-item">
            <div class="d-flex flex-column align-items-center justify-content-center text-white text-center p-5"
                 style="background: linear-gradient(rgba(0,0,0,0.35), rgba(0,0,0,0.35)), url('/images/carousel/slide2.png') center/cover no-repeat; min-height: 320px;">
                <h1 class="fw-bold">Найдите лучших сотрудников</h1>
                <p class="fs-5 mb-0">Публикуйте вакансии и просматривайте резюме соискателей</p>
            </div>
        </div>
        <div class="carousel-item">
            <div class="d-flex flex-column align-items-center justify-content-center text-white text-center p-5"
                 style="background: linear-gradient(rgba(0,0,0,0.35), rgba(0,0,0,0.35)), url('/images/carousel/slide3.jpg') center/cover no-repeat; min-height: 320px;">
                <h1 class="fw-bold">Быстро и просто</h1>
                <p class="fs-5 mb-0">Регистрация занимает меньше минуты</p>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#mainCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Назад</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#mainCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Вперёд</span>
    </button>
</div>

<#if vacancies?? && (vacancies?size > 0)>
<div class="mb-5">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Свежие вакансии</h3>
        <a href="/register" class="btn btn-outline-primary btn-sm">Смотреть все вакансии</a>
    </div>
    <div class="row g-3">
        <#list vacancies as vacancy>
        <div class="col-12">
            <div class="card shadow-sm border-0">
                <div class="card-body d-flex justify-content-between align-items-center flex-wrap gap-2">
                    <div>
                        <h5 class="card-title text-primary mb-1">
                            <a href="/vacancies/${vacancy.id}" class="text-decoration-none">${vacancy.title!""}</a>
                        </h5>
                        <p class="card-text text-muted mb-0">
                            <small>
                                <#if vacancy.categoryName??>
                                ${vacancy.categoryName}
                                <#else>
                                Категория не указана
                            </#if>
                            </small>
                        </p>
                    </div>
                    <p class="card-text text-success fw-bold mb-0">
                        <#if vacancy.salary??>
                            ${vacancy.salary} KGS
                        <#else>
                            Договорная
                        </#if>
                    </p>
                </div>
            </div>
        </div>
    </#list>
    </div>
</div>
</#if>

<div class="row justify-content-center text-center g-4 mb-5">
    <div class="col-md-5">
        <div class="p-4 bg-white rounded shadow-sm h-100">
            <h3 class="mb-3">Ищу работу</h3>
            <p class="text-muted">Создайте резюме и откликайтесь на вакансии</p>
            <a href="/register" class="btn btn-primary">Начать поиск</a>
        </div>
    </div>
    <div class="col-md-5">
        <div class="p-4 bg-white rounded shadow-sm h-100">
            <h3 class="mb-3">Ищу сотрудников</h3>
            <p class="text-muted">Разместите вакансию и найдите кандидатов</p>
            <a href="/register" class="btn btn-success">Разместить вакансию</a>
        </div>
    </div>
</div>

</@layout.mainLayout>

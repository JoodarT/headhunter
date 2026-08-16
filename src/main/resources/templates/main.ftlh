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
                 style="background: linear-gradient(135deg, #0d6efd, #0a58ca); min-height: 320px;">
                <h1 class="fw-bold">Найдите работу мечты</h1>
                <p class="fs-5 mb-0">Тысячи вакансий от проверенных работодателей</p>
            </div>
        </div>
        <div class="carousel-item">
            <div class="d-flex flex-column align-items-center justify-content-center text-white text-center p-5"
                 style="background: linear-gradient(135deg, #198754, #146c43); min-height: 320px;">
                <h1 class="fw-bold">Найдите лучших сотрудников</h1>
                <p class="fs-5 mb-0">Публикуйте вакансии и просматривайте резюме соискателей</p>
            </div>
        </div>
        <div class="carousel-item">
            <div class="d-flex flex-column align-items-center justify-content-center text-white text-center p-5"
                 style="background: linear-gradient(135deg, #6f42c1, #59359a); min-height: 320px;">
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

<div class="row justify-content-center text-center g-4 mb-5">
    <div class="col-md-5">
        <div class="p-4 bg-white rounded shadow-sm h-100">
            <h3 class="mb-3">Я ищу работу</h3>
            <p class="text-muted">Создайте резюме и откликайтесь на вакансии</p>
            <a href="/register" class="btn btn-primary">Начать поиск</a>
        </div>
    </div>
    <div class="col-md-5">
        <div class="p-4 bg-white rounded shadow-sm h-100">
            <h3 class="mb-3">Я ищу сотрудников</h3>
            <p class="text-muted">Разместите вакансию и найдите кандидатов</p>
            <a href="/register" class="btn btn-success">Разместить вакансию</a>
        </div>
    </div>
</div>

</@layout.mainLayout>

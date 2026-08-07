<#import "layout.ftlh" as layout>
<@layout.mainLayout title="Регистрация">
<div class="row justify-content-center">
    <div class="col-md-6 bg-white p-4 rounded shadow-sm">
        <h2 class="mb-3">Регистрация</h2>
        <form action="/api/users/register" method="post">
            <div class="mb-3">
                <label class="form-label">Имя</label>
                <input type="text" name="name" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Пароль</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Телефон</label>
                <input type="text" name="phone" class="form-control">
            </div>
            <div class="mb-3">
                <label class="form-label">Тип учетной записи</label>
                <select name="accountType" class="form-select" required>
                    <option value="APPLICANT">Соискатель</option>
                    <option value="EMPLOYER">Работодатель</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
        </form>
    </div>
</div>
</@layout.mainLayout>
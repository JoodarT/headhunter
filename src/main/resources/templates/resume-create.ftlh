<#import "layout.ftlh" as main>
<@main.mainLayout title="Создание резюме">
<div class="container mt-4">
    <h2>Создание резюме</h2>

    <form action="/resumes/create" method="post" id="resumeForm">
        <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </#if>

    <#-- Основная информация -->
    <div class="card mb-4">
        <div class="card-header fw-bold">Основная информация</div>
        <div class="card-body">
            <div class="mb-3">
                <label for="title" class="form-label">Название должности / резюме*</label>
                <input type="text" class="form-control" id="title" name="title" required placeholder="Например: Java Developer">
            </div>

            <div class="mb-3">
                <label for="category" class="form-label">Категория / Сфера деятельности*</label>
                <input type="text" class="form-control" id="category" name="category" required placeholder="Например: IT, разработка">
            </div>

            <div class="mb-3">
                <label for="applicantName" class="form-label">Имя и Фамилия соискателя</label>
                <input type="text" class="form-control" id="applicantName" name="applicantName" placeholder="Иван Иванов">
            </div>

            <div class="mb-3">
                <label for="expectedSalary" class="form-label">Желаемая зарплата*</label>
                <input type="number" step="0.01" class="form-control" id="expectedSalary" name="expectedSalary" required placeholder="100000">
            </div>

            <div class="mb-3">
                <label for="skills" class="form-label">Ключевые навыки</label>
                <textarea class="form-control" id="skills" name="skills" rows="3" placeholder="Java, Spring Boot, PostgreSQL, Docker..."></textarea>
            </div>
        </div>
    </div>

    <#-- Контактная информация -->
    <div class="card mb-4">
        <div class="card-header fw-bold">Контактная информация</div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.phone" class="form-label">Телефон</label>
                    <input type="text" class="form-control" id="contactInfo.phone" name="contactInfo.phone" placeholder="+7 999 000-00-00">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="contactInfo.email" name="contactInfo.email" placeholder="example@mail.com">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.telegram" class="form-label">Telegram</label>
                    <input type="text" class="form-control" id="contactInfo.telegram" name="contactInfo.telegram" placeholder="@username">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="contactInfo.linkedin" class="form-label">LinkedIn</label>
                    <input type="text" class="form-control" id="contactInfo.linkedin" name="contactInfo.linkedin" placeholder="linkedin.com/in/...">
                </div>
            </div>
        </div>
    </div>

    <#-- Опыт работы -->
    <div class="card mb-4">
        <div class="card-header d-flex justify-content-between align-items-center fw-bold">
            <span>Опыт работы</span>
            <button type="button" class="btn btn-sm btn-outline-primary" onclick="addExperience()">+ Добавить место работы</button>
        </div>
        <div class="card-body" id="experienceContainer">
            <div class="experience-item border p-3 mb-3 rounded" id="exp-0">
                <div class="mb-2">
                    <label class="form-label">Компания / Организация*</label>
                    <input type="text" class="form-control" name="experiences[0].companyName" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Должность*</label>
                    <input type="text" class="form-control" name="experiences[0].position" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Период работы (напр. 2021 - 2023)</label>
                    <input type="text" class="form-control" name="experiences[0].period" placeholder="2021 - По настоящее время">
                </div>
                <div class="mb-2">
                    <label class="form-label">Обязанности и достижения</label>
                    <textarea class="form-control" name="experiences[0].responsibilities" rows="2"></textarea>
                </div>
            </div>
        </div>
    </div>

    <#-- Образование -->
    <div class="card mb-4">
        <div class="card-header d-flex justify-content-between align-items-center fw-bold">
            <span>Образование</span>
            <button type="button" class="btn btn-sm btn-outline-primary" onclick="addEducation()">+ Добавить место учебы</button>
        </div>
        <div class="card-body" id="educationContainer">
            <div class="education-item border p-3 mb-3 rounded" id="edu-0">
                <div class="mb-2">
                    <label class="form-label">Учебное заведение</label>
                    <input type="text" class="form-control" name="educations[0].institution">
                </div>
                <div class="mb-2">
                    <label class="form-label">Специальность / Факультет</label>
                    <input type="text" class="form-control" name="educations[0].faculty">
                </div>
                <div class="mb-2">
                    <label class="form-label">Год окончания</label>
                    <input type="number" class="form-control" name="educations[0].graduationYear" placeholder="2023">
                </div>
            </div>
        </div>
    </div>

    <button type="submit" class="btn btn-primary btn-lg mb-5">Сохранить резюме</button>
    </form>
</div>

<script>
    let expCount = 1;
    function addExperience() {
        const container = document.getElementById('experienceContainer');
        const html = `
            <div class="experience-item border p-3 mb-3 rounded" id="exp-` + expCount + `">
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn-close" onclick="this.parentElement.parentElement.remove()"></button>
                </div>
                <div class="mb-2">
                    <label class="form-label">Компания / Организация*</label>
                    <input type="text" class="form-control" name="experiences[` + expCount + `].companyName" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Должность*</label>
                    <input type="text" class="form-control" name="experiences[` + expCount + `].position" required>
                </div>
                <div class="mb-2">
                    <label class="form-label">Период работы</label>
                    <input type="text" class="form-control" name="experiences[` + expCount + `].period" placeholder="2021 - По настоящее время">
                </div>
                <div class="mb-2">
                    <label class="form-label">Обязанности и достижения</label>
                    <textarea class="form-control" name="experiences[` + expCount + `].responsibilities" rows="2"></textarea>
                </div>
            </div>`;
        container.insertAdjacentHTML('beforeend', html);
        expCount++;
    }

    let eduCount = 1;
    function addEducation() {
        const container = document.getElementById('educationContainer');
        const html = `
            <div class="education-item border p-3 mb-3 rounded" id="edu-` + eduCount + `">
                <div class="d-flex justify-content-end">
                    <button type="button" class="btn-close" onclick="this.parentElement.parentElement.remove()"></button>
                </div>
                <div class="mb-2">
                    <label class="form-label">Учебное заведение</label>
                    <input type="text" class="form-control" name="educations[` + eduCount + `].institution">
                </div>
                <div class="mb-2">
                    <label class="form-label">Специальность / Факультет</label>
                    <input type="text" class="form-control" name="educations[` + eduCount + `].faculty">
                </div>
                <div class="mb-2">
                    <label class="form-label">Год окончания</label>
                    <input type="number" class="form-control" name="educations[` + eduCount + `].graduationYear" placeholder="2023">
                </div>
            </div>`;
        container.insertAdjacentHTML('beforeend', html);
        eduCount++;
    }
</script>
</@main.mainLayout>
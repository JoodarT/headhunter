(function () {
    var STORAGE_KEY = 'vacancyFilters';

    var form = document.getElementById('vacancyFilterForm');
    var searchInput = document.getElementById('searchInput');
    var categorySelect = document.getElementById('categorySelect');
    var sortSelect = document.getElementById('sortSelect');
    var directionSelect = document.getElementById('directionSelect');
    var resultsContainer = document.getElementById('vacancyResults');
    var paginationNav = document.getElementById('vacancyPagination');
    var companyAlert = document.getElementById('companyFilterAlert');
    var companyName = document.getElementById('companyFilterName');
    var companyReset = document.getElementById('companyFilterReset');
    var cardTemplate = document.getElementById('vacancyCardTemplate');
    var config = document.getElementById('vacanciesConfig');

    if (!form || !resultsContainer || !cardTemplate || !config) {
        return;
    }

    var currentCompany = config.dataset.initialCompany || '';

    function readStoredFilters() {
        try {
            var raw = window.localStorage.getItem(STORAGE_KEY);
            return raw ? JSON.parse(raw) : null;
        } catch (e) {
            return null;
        }
    }

    function saveFilters(filters) {
        try {
            window.localStorage.setItem(STORAGE_KEY, JSON.stringify(filters));
        } catch (e) {
            return;
        }
    }

    function readFiltersFromForm() {
        return {
            search: searchInput.value.trim(),
            category: categorySelect.value,
            sort: sortSelect.value,
            direction: directionSelect.value
        };
    }

    function applyFiltersToForm(filters) {
        searchInput.value = filters.search || '';
        categorySelect.value = filters.category || '';
        sortSelect.value = filters.sort || '';
        directionSelect.value = filters.direction || 'desc';
    }

    function buildParams(filters, page) {
        var params = new URLSearchParams();
        if (filters.search) {
            params.set('search', filters.search);
        }
        if (filters.category) {
            params.set('category', filters.category);
        }
        if (filters.sort) {
            params.set('sort', filters.sort);
        }
        if (filters.direction) {
            params.set('direction', filters.direction);
        }
        if (!filters.search && currentCompany) {
            params.set('company', currentCompany);
        }
        params.set('page', page);
        return params;
    }

    function renderCompanyAlert() {
        if (currentCompany) {
            companyName.textContent = currentCompany;
            companyAlert.hidden = false;
        } else {
            companyAlert.hidden = true;
        }
    }

    function renderVacancy(vacancy) {
        var node = cardTemplate.content.cloneNode(true);

        var titleLink = node.querySelector('.vacancy-title-link');
        titleLink.textContent = vacancy.title || '';
        titleLink.href = '/vacancies/' + vacancy.id;

        var companyWrap = node.querySelector('.vacancy-company-wrap');
        var companyLink = node.querySelector('.vacancy-company-link');
        if (vacancy.company) {
            companyWrap.hidden = false;
            companyLink.textContent = vacancy.company;
            companyLink.href = '/vacancies?company=' + encodeURIComponent(vacancy.company);
        } else {
            companyWrap.hidden = true;
        }

        var categoryText = node.querySelector('.vacancy-category-text');
        categoryText.textContent = config.dataset.labelCategory + ' ' + (vacancy.categoryName || config.dataset.labelCategoryNone);

        var responsesText = node.querySelector('.vacancy-responses-text');
        responsesText.textContent = config.dataset.responsesTemplate.replace('{0}', vacancy.responsesCount || 0);

        node.querySelector('.vacancy-description-text').textContent = vacancy.description || '';

        var salaryText = node.querySelector('.vacancy-salary-text');
        salaryText.textContent = vacancy.salary != null
            ? (vacancy.salary + ' ' + config.dataset.currency)
            : config.dataset.labelNegotiable;

        var detailsLink = node.querySelector('.vacancy-details-link');
        detailsLink.textContent = config.dataset.labelDetails;
        detailsLink.href = '/vacancies/' + vacancy.id;

        return node;
    }

    function renderResults(vacancies) {
        resultsContainer.innerHTML = '';

        if (!vacancies || vacancies.length === 0) {
            var empty = document.createElement('div');
            empty.className = 'col-12 vacancy-empty';
            empty.innerHTML = '<div class="alert alert-info text-center py-4"></div>';
            empty.querySelector('.alert').textContent = config.dataset.labelEmpty;
            resultsContainer.appendChild(empty);
            return;
        }

        vacancies.forEach(function (vacancy) {
            resultsContainer.appendChild(renderVacancy(vacancy));
        });
    }

    function renderPagination(currentPage, totalPages, filters) {
        paginationNav.innerHTML = '';

        if (totalPages <= 1) {
            return;
        }

        var ul = document.createElement('ul');
        ul.className = 'pagination justify-content-center';

        function pageHref(page) {
            var params = buildParams(filters, page);
            return '/vacancies?' + params.toString();
        }

        function addItem(page, label, disabled, active) {
            var li = document.createElement('li');
            li.className = 'page-item' + (disabled ? ' disabled' : '') + (active ? ' active' : '');
            var a = document.createElement('a');
            a.className = 'page-link';
            a.href = pageHref(page);
            a.dataset.page = page;
            a.textContent = label;
            li.appendChild(a);
            ul.appendChild(li);
        }

        addItem(currentPage - 1, config.dataset.labelPrev, currentPage <= 0, false);
        for (var p = 0; p < totalPages; p++) {
            addItem(p, String(p + 1), false, p === currentPage);
        }
        addItem(currentPage + 1, config.dataset.labelNext, currentPage >= totalPages - 1, false);

        paginationNav.appendChild(ul);
    }

    function loadVacancies(page, filtersOverride) {
        var filters = filtersOverride || readFiltersFromForm();
        var params = buildParams(filters, page);

        fetch('/vacancies/data?' + params.toString())
            .then(function (response) { return response.json(); })
            .then(function (data) {
                renderResults(data.vacancies);
                renderPagination(data.currentPage, data.totalPages, filters);
                renderCompanyAlert();

                var url = new URL(window.location.href);
                url.search = params.toString();
                window.history.replaceState(null, '', url);

                saveFilters(filters);
            });
    }

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        currentCompany = '';
        loadVacancies(0);
    });

    [categorySelect, sortSelect, directionSelect].forEach(function (select) {
        select.addEventListener('change', function () {
            loadVacancies(0);
        });
    });

    paginationNav.addEventListener('click', function (e) {
        var link = e.target.closest('a[data-page]');
        if (!link) {
            return;
        }
        e.preventDefault();
        var li = link.closest('.page-item');
        if (li && li.classList.contains('disabled')) {
            return;
        }
        loadVacancies(parseInt(link.dataset.page, 10));
    });

    companyReset.addEventListener('click', function (e) {
        e.preventDefault();
        currentCompany = '';
        loadVacancies(0);
    });

    var url = new URL(window.location.href);
    var hasUrlFilters = url.searchParams.has('search') || url.searchParams.has('category')
        || url.searchParams.has('sort') || url.searchParams.has('direction')
        || url.searchParams.has('company') || url.searchParams.has('page');

    if (!hasUrlFilters) {
        var stored = readStoredFilters();
        if (stored) {
            applyFiltersToForm(stored);
            loadVacancies(0, stored);
        }
    }
})();

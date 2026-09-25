(function () {

    function initDynamicSection(options) {
        var container = document.getElementById(options.containerId);
        var template = document.getElementById(options.templateId);
        var addBtn = document.getElementById(options.addBtnId);
        var itemClass = options.itemClass;
        var fieldPrefix = options.fieldPrefix;
        var minItems = options.minItems || 0;

        if (!container || !addBtn) {
            return;
        }

        function reindex() {
            var items = container.querySelectorAll('.' + itemClass);
            items.forEach(function (item, index) {
                item.querySelectorAll('[name]').forEach(function (field) {
                    field.name = field.name.replace(
                        new RegExp(fieldPrefix + '\\[\\d+\\]'),
                        fieldPrefix + '[' + index + ']'
                    );
                });
                var removeBtn = item.querySelector('.remove-item-btn');
                if (removeBtn) {
                    removeBtn.style.display = items.length > minItems ? '' : 'none';
                }
            });
        }

        addBtn.addEventListener('click', function () {
            if (!template) {
                return;
            }
            container.appendChild(template.content.cloneNode(true));
            reindex();
        });

        container.addEventListener('click', function (e) {
            var removeBtn = e.target.closest('.remove-item-btn');
            if (!removeBtn) {
                return;
            }
            var item = removeBtn.closest('.' + itemClass);
            var items = container.querySelectorAll('.' + itemClass);
            if (item && items.length > minItems) {
                item.remove();
                reindex();
            }
        });

        reindex();
    }

    function formatMonthYear(value) {
        if (!value) {
            return '';
        }
        var parts = value.split('-');
        var year = parseInt(parts[0], 10);
        var monthIndex = parseInt(parts[1], 10) - 1;
        var date = new Date(Date.UTC(year, monthIndex, 1));
        var lang = document.documentElement.lang || 'ru';
        var formatted = new Intl.DateTimeFormat(lang, { month: 'long', year: 'numeric' }).format(date);
        return formatted.charAt(0).toUpperCase() + formatted.slice(1);
    }

    function syncPeriod(block) {
        var startInput = block.querySelector('.period-start');
        var endInput = block.querySelector('.period-end');
        var currentCheckbox = block.querySelector('.period-current');
        var hidden = block.querySelector('.period-value');

        if (!startInput || !hidden) {
            return;
        }

        var isCurrent = currentCheckbox && currentCheckbox.checked;

        if (endInput) {
            endInput.disabled = isCurrent;
            if (isCurrent) {
                endInput.value = '';
            }
        }

        var startText = formatMonthYear(startInput.value);
        if (!startText) {
            return;
        }

        var currentLabel = currentCheckbox
            ? currentCheckbox.closest('label').querySelector('.form-check-label').textContent
            : '';
        var endText = isCurrent ? currentLabel : formatMonthYear(endInput ? endInput.value : '');

        hidden.value = endText ? (startText + ' — ' + endText) : startText;
    }

    function initPeriodPicker(containerId) {
        var container = document.getElementById(containerId);
        if (!container) {
            return;
        }

        container.addEventListener('input', function (e) {
            if (e.target.classList.contains('period-start') || e.target.classList.contains('period-end')) {
                syncPeriod(e.target.closest('.experience-item'));
            }
        });

        container.addEventListener('change', function (e) {
            if (e.target.classList.contains('period-current')) {
                syncPeriod(e.target.closest('.experience-item'));
            }
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        initDynamicSection({
            containerId: 'experienceContainer',
            templateId: 'experienceTemplate',
            addBtnId: 'addExperienceBtn',
            itemClass: 'experience-item',
            fieldPrefix: 'experiences',
            minItems: 1
        });

        initDynamicSection({
            containerId: 'educationContainer',
            templateId: 'educationTemplate',
            addBtnId: 'addEducationBtn',
            itemClass: 'education-item',
            fieldPrefix: 'educations',
            minItems: 0
        });

        initPeriodPicker('experienceContainer');
    });
})();

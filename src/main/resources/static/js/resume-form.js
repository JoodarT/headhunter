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
    });
})();

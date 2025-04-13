function addModule() {
	console.log("Added new module")

	const courseId = document.getElementById('courseId').value;
	const csrfToken = document.getElementById('csrfToken').value;

	// Создаем новый элемент
	const newField = document.createElement('div');
	newField.classList.add('module');
	newField.innerHTML = `
				<form id="modules-form" action="/teacher/${courseId}" method="post">
					<input type="hidden" name="_csrf" value="${csrfToken}"/>
	                <input placeholder="Название" class="input-text" type="text" name="title" required />
					<input placeholder="Описание" class="input-text" type="text" name="description" required />
					<input class="blue-btn save-btn" type="submit" value="Сохранить">
				</form>
				`;

	// Добавляем элемент в контейнер
	document.getElementById('modules-section').appendChild(newField)
}


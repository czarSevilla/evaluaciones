var EVAL = {};

EVAL.TIMEOUT = 3000;

EVAL.selectAll = function(selectSourceId, selectTargetId) {
	$("select[id='" + selectSourceId + "'] option").each(function(idx){
		var option = $(this);
		$("select[id='" + selectTargetId + "']").append(option);
	});
	$("select[id='" + selectSourceId + "']").empty();
};

EVAL.selectSome = function(selectSourceId, selectTargetId) {
	$("select[id='" + selectSourceId + "'] option:selected")
		.remove()
		.appendTo("select[id='" + selectTargetId + "']")
		.removeAttr('selected');	
};

EVAL.beforeSubmit = function(formId, selectTargetId) {
	$("select[id='" + selectTargetId + "'] option").prop('selected', true);
	$('#' + formId).submit();
}

EVAL.modalSubmit = function(formId) {
	$('#' + formId).submit();
}

EVAL.changePage = function(number) {
	$("#currentPage", "#form-eval").val(number);
	$("#form-eval").submit();
}

EVAL.finishEval = function() {
	$("#step", "#form-eval").val(3);
	$("#form-eval").submit();
}

EVAL.timeoutEval = function() {
	$("#step", "#form-eval").val(3);
	$("#timeout").val(1);
	$("#form-eval").submit();
}

EVAL.edit = function(idInput, idValue) {
	$("#" + idInput, "#edit-form").val(idValue);
	$("#edit-form").submit();
};

EVAL.toDelete = function(idInput, idValue) {
	$("#" + idInput, "#delete-form").val(idValue);
}

EVAL.doDelete = function() {
	$("#delete-form").submit();
}

EVAL.resetPassword = function(idInput, idValue) {
	$("#" + idInput, "#reset-form").val(idValue);
	$("#reset-form").submit();
};

EVAL.changeStatus = function(idInput, idValue, idStatus, statusValue) {
	$("#" + idInput, "#change-form").val(idValue);
	$("#" + idStatus, "#change-form").val(statusValue);
	$("#change-form").submit();
}

EVAL.addAnswer = function() {
	var answer = $("#text-answer").val();
	if (answer == null || answer.trim() == "") {
		return;
	}
	var correct = $("#correct-answer").is(':checked');
	
	var $html = $('#answer-template').clone();
	$html.removeAttr('id');
	$html.removeAttr('style');
	$html.find('.answer-text').text(answer);
	$html.find('input[name=answersTexts]').val(answer);
	if (correct) {
		$html.find('.answer-correct-no').remove();
		$html.find('input[name=answersCorrects]').val('1');
	} else {
		$html.find('.answer-correct-ok').remove();
		$html.find('input[name=answersCorrects]').val('0');
	}
	$html.appendTo('#body-answers');
	$('#text-answer').val('');
	$('#correct-answer').removeAttr("checked");
	$('#text-answer').focus();
};

EVAL.removeAnswer = function(btn) {
	$(btn).closest('.answer-item').remove();
}

EVAL.addCategory = function() {
	var description = $('#category-description').val();
	if (description == null || description.trim() == "") {
		return;
	}		
	var $html = $('#category-template').clone();
	$html.removeAttr('id');
	$html.removeAttr('style');
	$html.find('.category-description').text(description);
	$html.find('input[name=categoriesDescriptions]').val(description);
	$html.appendTo('#body-categories');
	$('#category-description').val('');
}

EVAL.removeCategory = function(btn) {
	$(btn).closest('.category-item').remove();
}

EVAL.findBy = function(category) {
	$('#text').val('');
	$('#searchStatus option:first').attr('selected', 'selected');
	$('#searchCategory').val(category);
	$('#form-eval').submit();
}

EVAL.search = function() {
	$('#searchCategory').val('');
	$('#form-eval').submit();
}

EVAL.clear = function() {
	$('#searchCategory').val('');
	$('#text').val('');
	$('#searchStatus option:first').attr('selected', 'selected');
	$('#form-eval').submit();
}

EVAL.resetSearch = function() {
	$('#name').val('');
	$('#searchStatus option:first').attr('selected', 'selected');
	$('#searchResultado option:first').attr('selected', 'selected');
	$('#searchManager option:first').attr('selected', 'selected');
	$('#form-eval').submit();
}
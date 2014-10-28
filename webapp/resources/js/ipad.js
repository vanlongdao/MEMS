function hiddenDate(dateField) {
	var name = dateField.attr("name");
	var value = dateField.val();
	var hidden = $("<input></input>");
	hidden.prop("type", "hidden");
	hidden.prop("name", name);
	hidden.prop("id", "hid" + name);
	hidden.val(dateField.val());
	dateField.after(hidden);
	dateField.removeAttr("name");

	var dateValue = value.match(/^(\d{4})-(\d{1,2})-(\d{1,2})$/);
	if (dateValue)
		dateField.val(dateValue[3] + "/" + dateValue[2] + "/" + dateValue[1]);
	return hidden;
}

$(document).ready(function() {
	$("#growl-popup").click(function() {
		// PrimeFaces.widgets.ipad_growl.removeAll();
		$("#growl-popup").hide();
	});
});

document.onkeypress = function(e) {
	if(e.which == 13)
	return false;
};

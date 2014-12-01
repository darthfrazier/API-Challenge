/**
 * @author Darth Frazier
 */
var login = {
	"email":"kmjf@princeton.edu",
	"github":"https://github.com/darthfrazier"
}
var token;

$(document).ready(function(){
	$.ajax({
		
		url: "http://challenge.code2040.org/api/register",
		type:'POST',
		crossDomain: true,
		dataType: 'json',
		data:JSON.stringify(login)
	}).done(function (data) {
		$(#token).val(data.result);
		token = data.result;
	});
});

var jsontoken = {
	"token" = token
}
});
//주소 select box 값 넘기기
function addressVal() {
	var address = document.getElementById("address");

	address.options[address.selectedIndex].val;
	console.log($(address).val());
}

//성별 check box 값 넘기기
$(document).ready(function() {
	$("#reg").click(function(){
		var test = $(this).val();

		$("input[name=gender]:checked").each(function() {
			console.log("성별 : " + test);
		});
	});
});

//회원가입 공백 유효성
$(function(){
	$('#reg').attr("disabled", "disabled");

	$('input').keyup(function(){
		var name=$('#name').val();
		var email=$('#email').val();
		var password=$('#password').val();
		var password2=$('#password2').val();

		if(name == '' || email == '' || password == '' || password2 == ''){
			$('#reg').attr("disabled", "disabled");
			
		} else {
			$('#reg').removeAttr("disabled");
		}
	});
});

//비밀번호일치 유효성
$(function() {
	$("#alert-success").hide();
	$("#alert-danger").hide();
	$("input").keyup(function() {
		var password=$('#password').val();
		var password2=$('#password2').val();
		
		if (password != "" || password2 != "") {
			if (password == password2) {
				$("#alert-success").show();
				$("#alert-danger").hide();
			} else {
				$("#alert-success").hide();
				$("#alert-danger").show();
			}
		}
	});
});
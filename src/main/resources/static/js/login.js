let $btnLogin = $('#btn-login');

$(document).ready(function() {
	let params = new URLSearchParams(window.location.search);
	
	// spring security에서 파라미터로 error를 보낼 경우 모달 표출
	if(params.has('error')) {
		showModal('아이디 혹은 패스워드를 확인해주세요.');
	}
});

$btnLogin.click(function() {
	let id = $('#id');
	let pwd = $('#pwd');
	
	if(id.val().trim() == '') {
		showModal('아이디를 입력해주세요.');
	} else if(pwd.val().trim() == '') {
		showModal('패스워드를 입력해주세요.');
	} else {
		let $f = $('#f');
		$f.attr('method', 'POST');
		$f.attr('action', '/login');
		$f.submit();
	}
});

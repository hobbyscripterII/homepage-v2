let $btnLogin = $('#btn-login');

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
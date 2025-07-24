let $btnLogin = $('#btn-login');

$(document).ready(function() {
	// ---------- 관리자 화면 ----------
	// 메뉴 변경 시 화면 이동
	let $menuList = $('.menu-list');

	$menuList.on('change', function() {
		let selectedVal = $(this).val();
		location.href = `/a/${selectedVal}?page=1`;
	});
	
	// path에 있는 값이 select box의 카테고리명과 일치할 때 select box의 값을 변경
	let currentPath = window.location.pathname.split('/').pop();
	let $optionVal = $menuList.find('option[value="' + currentPath + '"]');
	
	if($optionVal.length > 0) {
		$menuList.val(currentPath);
	}
	
	// ---------- 로그인 실패 ----------
	let params = new URLSearchParams(window.location.search);
	
	// spring security에서 파라미터로 error를 보낼 경우 모달 표출
	if(params.has('error')) {
		showModal('아이디 혹은 패스워드를 확인해주세요.');
	}
});

// ---------- 로그인 ----------
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

// ---------- 불필요 파일 제거 ----------
function removeFile() {
	const CONFIRM_MSG = '불필요 이미지 파일을 제거하시겠습니까?';

	showConfirmModal(CONFIRM_MSG,
		function() {
			$.ajax({
				type : 'DELETE',
				url : `/a/rm`,
				processData : false,
				contentType : false,
				success : (data) => {
					let status = data.status;
					const SUCCESS_CODE = 200;
					
					if(status == SUCCESS_CODE) {
						const MSG = '불필요 이미지 파일이 제거되었습니다.';
						
						showModal(MSG);
					}
				}, error : (error) => {
					showModal(error);
				}
			});
	});
}
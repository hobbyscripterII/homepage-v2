let editor = null; // ckeditor5

$(document).ready(function() {
	// 썸네일
	let $thumbnailBtn = $('#thumbnail-btn');
	let $thumbnail = $('#thumbnail');
	
	$thumbnailBtn.click(function() {
		$thumbnail.click();
	});
});

let $menuList = $('.menu-list');

$menuList.on('change', function() {
	let $youtubeId = $('#youtubeId');
	let $thumbnailPreview = $('#thumbnail-preview');
	let $thumbnailBtn = $('#thumbnail-btn');
	let selectedVal = $(this).val();
	let music = 'music';
	
	// 음악 게시판일 경우 유튜브 아이디 입력 폼 표출 및 썸네일 등록 버튼 숨김
	if(selectedVal == music) {
		$youtubeId.attr('type', 'text');
		$thumbnailBtn.css('display', 'none');
	} else {
		$youtubeId.attr('type', 'hidden');
		$thumbnailBtn.css('display', 'block');
	}
	
	// 썸네일 src 속성에 값이 있을 경우 제거 후 요소 숨김
	if($thumbnailPreview.attr('src')) {
		$thumbnailPreview.removeAttr('src');
		$thumbnailPreview.css('display', 'none');
	}
});

// 썸네일 미리보기 기능
function preview(file) {
	let thumbnail = file.files[0];
	let $thumbnailPreview = $('#thumbnail-preview');
	
	if(thumbnail) {
		let reader = new FileReader();
		
		reader.onload = function(e) {
			let attr = e.target.result;
			
			$thumbnailPreview.attr('src', attr);
			$thumbnailPreview.css('display', 'block');
		};
		
		reader.readAsDataURL(thumbnail);
	}
}

function youtubeIdGet(url) {
	let youtubeUrl = $(url).val();
	let youtubeId = youtubeUrl.split('v=')[1]?.split('&')[0];
	let attr = `https://img.youtube.com/vi/${youtubeId}/0.jpg`;
	let $youtubeIdEl = $('#youtubeId');
	let $thumbnailPreview = $('#thumbnail-preview');
	
	// 입력한 url에 유튜브 id가 있을 경우
	if(youtubeId.length == 11) {
		
		$thumbnailPreview.attr('src', attr);
		$thumbnailPreview.css('display', 'block');
	} else {
		showModal('유튜브 링크를 다시 한 번 확인해주세요.');
		
		$youtubeIdEl.val('');
		$thumbnailPreview.attr('src', '');
		$thumbnailPreview.css('display', 'none');
		
		return;
	}
}

function insert() {	
	let $menuList = $('.menu-list'); // 게시판 메뉴 카테고리
	let $title = $('#title');
	let $youtubeId = $('#youtubeId');
	let $thumbnail = $('#thumbnail')[0]?.files[0] ?? null;
	
	let menu = $menuList.val();
	let title = $title.val();
	let contents = editor.getData();
	let youtubeId = $youtubeId.val();
	let secYn = $('#secYn').is(':checked');
	
	if(title == '' || title == null) {
		showModal('제목을 입력해주세요.'); return;
	} else if(contents == '' || contents == null) {
		showModal('내용을 입력해주세요.'); return;
	} else if(menu == 'music' && youtubeId == '' || youtubeId == null) {
		showModal('유튜브 링크 주소를 입력해주세요.'); return;
	}
	
	const CONFIRM_MSG = '게시글을 등록하시겠습니까?';

	showConfirmModal(CONFIRM_MSG,
		function() {
			let formData = new FormData();
			let dto = {menu : menu, title : title, contents : contents, youtubeId : youtubeId, secYn : secYn};	

			formData.append('dto', new Blob([JSON.stringify(dto)], {
				type : 'application/json'
			}));

			if($thumbnail) {
				formData.append('thumbnail', $thumbnail);
			}

			$.ajax({
				type : 'POST',
				url : '/a/i',
				data : formData,
				processData : false,
				contentType : false,
				success : (data) => {
					let status = data.status;
					const SUCCESS_CODE = 200;
					
					if(status == SUCCESS_CODE) {
						let iboard = data.data.iboard;
						const CONFIRM_MSG = '게시글 등록이 완료되었습니다.<br>게시글을 확인하러 가시겠습니까?';
						
						showConfirmModal(CONFIRM_MSG,
							function() {
							let menu = $('.menu-list').val();
							
							location.href = `/b/${menu}/${iboard}`;
						}, function() {
							location.href = `/b/${menu}`;
						});
					}
				}, error : (error) => {
					console.error('error = ', error);
				}
			});
	});
}

function update() {	
	let $menuList = $('.menu-list'); // 게시판 메뉴 카테고리
	let $title = $('#title');
	let $youtubeId = $('#youtubeId');
	let $thumbnail = $('#thumbnail')[0]?.files[0] ?? null;
	
	let menu = $menuList.val();
	let title = $title.val();
	let contents = editor.getData();
	let youtubeId = $youtubeId.val();
	let secYn = $('#secYn').is(':checked');
	let iboard = $('#iboard').val(); // hidden
	
	if(title == '' || title == null) {
		showModal('제목을 입력해주세요.'); return;
	} else if(contents == '' || contents == null) {
		showModal('내용을 입력해주세요.'); return;
	} else if(menu == 'music' && youtubeId == '' || youtubeId == null) {
		showModal('유튜브 링크 주소를 입력해주세요.'); return;
	}
	
	const CONFIRM_MSG = '게시글을 수정하시겠습니까?';

	showConfirmModal(CONFIRM_MSG,
		function() {
			let formData = new FormData();
			let dto = {menu : menu, title : title, contents : contents, youtubeId : youtubeId, secYn : secYn};	

			formData.append('dto', new Blob([JSON.stringify(dto)], {
				type : 'application/json'
			}));

			if($thumbnail) {
				formData.append('thumbnail', $thumbnail);
			}

			$.ajax({
				type : 'PATCH',
				url : `/a/u/${iboard}`,
				data : formData,
				processData : false,
				contentType : false,
				success : (data) => {
					let status = data.status;
					const SUCCESS_CODE = 200;
					
					if(status == SUCCESS_CODE) {
						const CONFIRM_MSG = '게시글 수정이 완료되었습니다.<br>게시글을 확인하러 가시겠습니까?';
						
						showConfirmModal(CONFIRM_MSG,
							function() {
							let menu = $('.menu-list').val();
							let iboard = $('#iboard').val();
							
							location.href = `/b/${menu}/${iboard}`;
						}, function() {
							location.href = `/b/${menu}`;
						});
					}
				}, error : (error) => {
					console.error('error = ', error);
				}
			});
	});
}

function del() {
	const CONFIRM_MSG = '삭제된 게시글은 복구할 수 없습니다.<br>삭제하시겠습니까?';

	showConfirmModal(CONFIRM_MSG,
		function() {
			let iboard = $('#iboard').val();
			let boardUrl = $('#BOARD_URL').val();
			
			let dto = {iboard : iboard};
			
			$.ajax({
				type : 'DELETE',
				url : `/a/${boardUrl}/${iboard}`,
				data : JSON.stringify(dto),
				processData : false,
				contentType : false,
				success : (data) => {
					let status = data.status;
					const SUCCESS_CODE = 200;
					
					if(status == SUCCESS_CODE) {
						const MSG = '게시글 삭제가 완료되었습니다.<br>게시판 메인 화면으로 이동합니다.';
						showModal(MSG);
						
						setTimeout(() => {
							location.href = `/b/${boardUrl}`;
						}, 1000);
					}
				}, error : (error) => {
					console.error('error = ', error);
				}
			});
	});
}
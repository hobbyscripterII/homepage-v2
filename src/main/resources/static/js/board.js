// ck editor5 초기화
lassicEditor
    .create(document.querySelector('#editor'))
    .catch(error => {
        console.error(error);
    });

// 음악 게시판 선택 시 유튜브 아이디 등록 폼 표출
let $menuList = $('.menu-list');

$menuList.on('change', function() {
	let $youtubeId = $('#youtubeId');
	let selectedVal = $(this).val();
	let music = 'music';
	
	if(selectedVal == music) {
		$youtubeId.attr('type', 'text');
	} else {
		$youtubeId.attr('type', 'hidden');
	}
});
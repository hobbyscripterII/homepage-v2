$(document).ready(function () {
	let $modalClose = $('.modal-close, .modal-confirm, .modal-overlay');
	let $modalOverlay = $('.modal-overlay, .modal');
	
	// 모달 닫기
	$modalClose.on('click', function () {
	  $modalOverlay.fadeOut(200);
	});
});

// 모달 열기
function showModal(message) {
	let $modalMeesage = $('.modal-body p');
	let $modalOverlay = $('.modal-overlay, .modal');
	
  	$modalMeesage.text(message);
  	$modalOverlay.fadeIn(200);
}
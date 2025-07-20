// 콜백 함수 저장용 변수 선언
window.confirmYesCallback = null;
window.confirmCancelCallback = null;

$(document).ready(function () {
	let $modalClose = $('.modal-close, .modal-confirm, .modal-overlay');
	let $modalOverlay = $('.modal-overlay, .modal');
	
	let $modalYesBtn = $('.modal-yes'); // 확인 버튼
	let $modalCancelBtn = $('.modal-cancel'); // 취소 버튼
	
	// 공통 모달 닫기 버튼 클릭 시
	$modalClose.on('click', function () {
	  closeModal();
	});
	
	// 선택 모달 확인 버튼 클릭 시 콜백 함수 실행
	$modalYesBtn.on('click', function() {
		if(typeof window.confirmYesCallback == 'function') {
			window.confirmYesCallback();
		}
		
		closeModal();
	});
	
	// 선택 모달 취소 버튼 클릭 시 콜백 함수 실행
	$modalCancelBtn.on('click', function() {
		if(typeof window.confirmCancelCallback == 'function') {
			window.confirmCancelCallback();
		}

		closeModal();
	});
});

// 기본 모달 열기
function showModal(message) {
	let $modalMessage = $('#default-modal .modal-body p');
	let $modal = $('#default-modal');
	let $overlay = $('.modal-overlay');

	$modalMessage.html(message);
	$overlay.fadeIn(200);
	$modal.fadeIn(200);
}

// 선택 모달 열기
function showConfirmModal(message, yes, cancel) {
	let $modalMessage = $('#confirm-modal .modal-body p');
	let $modal = $('#confirm-modal');
	let $overlay = $('.modal-overlay');

	$modalMessage.html(message);
	window.confirmYesCallback = yes || null; // 확인 버튼 클릭 시 콜백 함수
	window.confirmCancelCallback = cancel || null; // 취소 버튼 클릭 시 콜백 함수
	$overlay.fadeIn(200);
	$modal.fadeIn(200); 
}

function closeModal() {
	let $modalOverlay = $('.modal-overlay');
	let $modal = $('.modal');
	
    $modalOverlay.fadeOut(200);
    $modal.fadeOut(200);
}
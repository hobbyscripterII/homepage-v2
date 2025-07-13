$(document).ready(function() {
	// nav 가로 스크롤 기능
	let $nav = $('nav');

	$nav.on('wheel', function(e) {
		if (e.originalEvent.deltaY === 0) return;
		e.preventDefault();

		let $this = $(this);
		let scrollAmount = (e.originalEvent.deltaY) * 2; // 가로 스크롤 이동 속도 2배

		$this.stop().animate({
			scrollLeft: $this.scrollLeft() + scrollAmount
		}, 100);
	})

	// nav active 기능
	let currentPath = window.location.pathname;
	let $navItems = $('nav a');

	$navItems.each(function() {
		let $linkPath = $(this).attr('href');

		if ((currentPath == $linkPath) || (currentPath.startsWith($linkPath + '/'))) {
			$(this).addClass('active');
		}
	});

	// nav active 시 오른쪽 메뉴일 경우
	let $navActive = $nav.find('.active');

	if ($navActive.length > 0) {
		let navLeft = $nav.offset().left;
		let navRight = navLeft + $nav.outerWidth();
		let activeLeft = $navActive.offset().left;
		let activeRight = activeLeft + $navActive.outerWidth();

		if ((activeRight > navRight) || (activeLeft < navLeft)) {
			let scrollLeft = $nav.scrollLeft() + (activeLeft - navLeft) - 16;

			$nav.animate({ scrollLeft: scrollLeft }, 300);
		}
	}
})
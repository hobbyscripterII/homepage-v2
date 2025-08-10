$(document).ready(function() {
	starfield();
	
	// setVh();
	$(window).on('resize', setVh);
	
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
});

function setVh() {
	let vh = window.innerHeight * 0.01;
	
	document.documentElement.style.setProperty('--vh', `${vh}px`);
}

function starfield() {
	let $body = $('body');
	let $container = $('<div class="starfield"></div>');
	
	$body.append($container);

	let colors = ['var(--pink-100)', 'var(--yell-100)', 'var(--pink-200)', 'var(--yell-200)'];
	let STAR_COUNT = window.matchMedia('(min-width: 768px)').matches ? 120 : 80;

	for(let i = 0; i < STAR_COUNT; i++) {
	    let $s = $('<span class="star"></span>');
	    let size = 4 + Math.random() * 4;
		
	    $s.css({
	        color : colors[Math.floor(Math.random() * colors.length)],
	        width : size + 'px',
	        height : size + 'px',
	        left : (Math.random() * 100) + '%'
	    });

	    let fall  = 6 + Math.random() * 6;
	    let delay = -Math.random() * 12;

	    $s.css({
	        animationDuration: fall + 's, ' + (2 + Math.random() * 2) + 's',
	        animationDelay: delay + 's, ' + (Math.random() * 2) + 's'
	    });

	    $container.append($s);
	}
}
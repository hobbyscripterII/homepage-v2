$(document).ready(function() {
    let $nav = $('nav');

    $nav.on('wheel', function(e) {
        if(e.originalEvent.deltaY === 0) return;
        e.preventDefault();

        let $this = $(this);
        let scrollAmount = (e.originalEvent.deltaY) * 2; // 가로 스크롤 이동 속도 2배배

        $this.stop().animate({
            scrollLeft: $this.scrollLeft() + scrollAmount
        }, 100);
    })
})
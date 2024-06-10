<!--返回顶部-->
$('#to-top-btn').click(function toTop() {
    $('html').animate({scrollTop: 0}, 300);
});

let popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
let popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl);
});

<!--滚动侦测-->
let waypoint = new Waypoint({
    element: document.getElementById('waypoint'),
    handler: function(direction){
        if (direction == 'down'){
            $('#toolbar').show(100); //100毫秒
        }else{
            $('#toolbar').hide(300);
        }
        console.log('Scrolled to waypoint!');
    }
});

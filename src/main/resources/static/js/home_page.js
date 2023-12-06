const change_color_index = 200;

function on_nav_bar_scroll() {
    let bar = $("nav_bar");

    if(document.body.scrollTop > change_color_index){
        bar.style.backgroundColor = "#156D00";
    } else {
        bar.style.backgroundColor = "rgba(0, 0, 0, 0)";
    }
}

function on_background_scroll() {
    let body = document.body;
    let mask = $("mask");

    if(body.scrollTop > change_color_index) {
        mask.style.boxShadow = "inset 0 0 0 3000px rgba(80, 80, 80, 0.4)";
        mask.style.filter = "blur(10px)";
    } else {
        mask.style.boxShadow = "";
        mask.style.filter = "";
    }
}
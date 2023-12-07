const change_color_index = 500;

function getElement(id) {return document.getElementById(id);}

function on_wiki_load() {
    // setInterval("on_nav_bar_scroll()", 100);
    setInterval("on_background_scroll()", 100);
    // setInterval("on_bottom_bar_scroll()", 100);
}

function on_nav_bar_scroll() {
    let bar = getElement("nav_bar");
    let scrY = window.scrollY;
    bar.style.transitionDuration = ".5s";
    if(scrY > change_color_index){
        bar.style.backgroundColor = "#156D00";
    } else {
        bar.style.backgroundColor = "transparent";
    }
}

function on_background_scroll() {
    let mask = getElement("mask");

    if(window.scrollY > change_color_index * .5) {
        mask.style.boxShadow = "inset 0 0 0 3000px rgba(80, 80, 80, 0.2)";
        mask.style.filter = "blur(10px)";
    } else {
        mask.style.boxShadow = "";
        mask.style.filter = "";
    }
}

function on_bottom_bar_scroll() {
    let bar = getElement("bottom_bar");

    if(window.scrollY > document.body.scrollHeight * 0.85) {
        bar.style.opacity = "1";
    } else {
        bar.style.opacity = "0";
    }
}
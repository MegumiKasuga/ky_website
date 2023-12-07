function on_load_bbs_entrance() {
    setInterval("on_bottom_bar_scroll(.55)", 100);
}

function on_bottom_bar_scroll(size) {
    let bar = getElement("bottom_bar");

    if(window.scrollY > document.body.scrollHeight * size) {
        bar.style.opacity = "1";
    } else {
        bar.style.opacity = "0";
    }
}

function on_load_bbs_convention() {
    setInterval("on_bottom_bar_scroll(.2)", 100);
}

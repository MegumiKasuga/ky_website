function on_load_gallery() {
    setInterval("on_bottom_bar_scroll()", 100);
}

function on_bottom_bar_scroll() {
    let bar = getElement("bottom_bar");

    if(window.scrollY > document.body.scrollHeight * 0.3) {
        bar.style.opacity = "1";
    } else {
        bar.style.opacity = "0";
    }
}
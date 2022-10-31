function btnClick(event) {
    let el = event.target;
    for (const child of $(this).parent().children()) {
        if (child.classList.contains("selected")) {
            child.classList.remove("selected");
            if (child === el && !el.classList.contains('r-size')) {
                document.getElementById(el.getAttribute("target")).value = "";
                return;
            }
        }
    }

    document.getElementById(el.getAttribute("target")).setAttribute("value", el.value);
    el.classList.add("selected");
}
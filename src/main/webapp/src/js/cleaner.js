function clean_input() {

    const elems = document.querySelectorAll(".selected");

    [].forEach.call(elems, function (el) {
        el.classList.remove("selected");
    });

    $('[id="form:x_value"]').val(undefined);
    chooseFirstRadius();
    $('[id="form:y_value"]').val(undefined);
}

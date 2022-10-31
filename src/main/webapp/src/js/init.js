const range = (start, end) => {
    const length = end - start;
    return Array.from({length}, (_, i) => start + i);
}

const circles = {};

const xRange = range(-5, 4);

function init() {
    printBtns(xRange, ".x-buttons", "x-dot", "form:x_value");
    chooseFirstRadius();
    $('.r-size').on("click", btnClick);
    $('[id="form:y_value"]').on('input', limit_length);
}

function computeClickCoords() {
    let target = $("#svg-graph");
    let r = $('[id="form:r_value"]').val();
    let x = Math.round(event.clientX - target.position().left);
    let y = event.clientY - target.position().top;
    let xValue = ((x - 150) / (100 / r)).toFixed(3);
    let yValue = ((y - 150) / (-100 / r)).toFixed(3);
    $('[id="form:x_value"]').val(xValue);
    $('[id="form:y_value"]').val(yValue);
    $('[id="form:submit"]').click();
}


function printBtns(btnsAr, parent, className, targetClass) {
    for (let i = 0; i < btnsAr.length; i++) {
        const btn = document.createElement("input");
        btn.type = "button";
        btn.value = btnsAr[i];
        const buttons = $(parent);
        btn.classList.add(className);
        btn.id = className + i;
        btn.setAttribute("target", targetClass);
        btn.addEventListener("click", btnClick);
        buttons.append(btn);
    }
}

function chooseFirstRadius() {
    const r_button = $(".r-buttons .r-size:first-child");
    r_button.addClass("selected");
    let id = 'form:r_value';
    $('[id="' + id + '"]').val(r_button.val());
    r_button.click();
}

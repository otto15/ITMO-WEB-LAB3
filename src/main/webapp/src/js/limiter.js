function limit_length(e) {
    let count = 10;
    if (e.target.value.indexOf('.') === -1) { return; }
    if ((e.target.value.length - e.target.value.indexOf('.')) > count) {
      e.target.value = e.target.value.slice(0, e.target.value.indexOf('.') + count + 1);
    }
}
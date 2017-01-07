function forEach(list, func) {
    for (var key in list) {
        if (list.hasOwnProperty(key)) {
            func.call(null, list[key]);
        }
    }
}

function checkedValue(radios) {
    for (var key in radios) {
        if (radios.hasOwnProperty(key)) {
            if (radios[key].checked) {
                return radios[key].value;
            }
        }
    }
    return null;
}
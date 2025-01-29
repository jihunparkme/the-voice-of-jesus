function searchRecordList(page) {
    if (page == undefined) {
        page = 0;
    }

    $("#page").val(page);
    $("#form").submit();
}

function search() {
    $("#page").val(0);
    $("#form").submit();
}
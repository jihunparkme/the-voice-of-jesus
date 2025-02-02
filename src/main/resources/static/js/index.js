function searchRecordList(page) {
    if (page == undefined) {
        page = 0;
    }

    $("#page").val(page);
    $("#form").submit();
}

function goSearch() {
    searchRecordList(0)
}

function goChannel(event) {
    const channel = event.target.getAttribute("data-value");
    if (channel === "ALL") {
        resetParam()
    }
    $("#channel").val(channel);
    searchRecordList(0)
}

function goPlayList(event) {
    $("#playList").val(event.target.getAttribute("data-value"));
    searchRecordList(0)
}

function resetParam() {
    document.getElementById('page').value = null;
    document.getElementById('channel').value = null;
    document.getElementById('search').value = null;
    document.getElementById('playList').value = null;
}

function resetFilter() {
    resetParam();
    searchRecordList(0)
}
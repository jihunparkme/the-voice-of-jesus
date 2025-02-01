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
    let channelName = event.target.getAttribute("data-value")
    $("#channel").val(channelName);
    searchRecordList(0)
}

function resetFilter() {
    document.getElementById('page').value = null;
    document.getElementById('channel').value = null;
    document.getElementById('search').value = null;
    document.getElementById('playList').value = null;
    searchRecordList(0)
}
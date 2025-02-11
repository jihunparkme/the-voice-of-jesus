function searchSermonList(page) {
    if (page == undefined) {
        page = 0;
    }

    document.getElementById('page').value = page;
    $("#form").submit();
}

function goSearch() {
    searchSermonList(0)
}

function goChannel(event) {
    const channel = event.target.getAttribute("data-value");
    if (channel === "ALL") {
        resetParam()
    }
    $("#channel").val(channel);
    searchSermonList(0)
}

function goPlayList(event) {
    $("#playList").val(event.target.getAttribute("data-value"));
    searchSermonList(0)
}

function resetParam() {
    document.getElementById('page').value = null;
    document.getElementById('channel').value = null;
    document.getElementById('search').value = null;
    document.getElementById('playList').value = null;
}

function resetFilter() {
    resetParam();
    searchSermonList(0)
}
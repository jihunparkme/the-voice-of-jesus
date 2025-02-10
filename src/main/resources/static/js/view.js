const button = document.getElementById("transcript-btn");
const transcript = document.getElementById("transcript");

button.addEventListener("click", function () {
    if (transcript.classList.contains("show")) {
        transcript.classList.remove("show")
        button.classList.add("collapsed")
        button.innerHTML = "전체 자막 펼치기"
    } else {
        transcript.classList.add("show")
        button.classList.remove("collapsed")
        button.innerHTML = "전체 자막 접기"
    }
});
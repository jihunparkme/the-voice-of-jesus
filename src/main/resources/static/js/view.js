const button = document.getElementById("transcript-btn");
const transcript = document.getElementById("transcript");

button.addEventListener("click", function () {
    if (transcript.style.display === "none") {
        transcript.style.display = "block";
        button.innerHTML = "전체 자막 접기 <i class=\"bi bi-caret-up-fill\"></i>"
    } else {
        transcript.style.display = "none";
        button.innerHTML = "전체 자막 펼치기 <i class=\"bi bi-caret-down-fill\"></i>"
    }
});
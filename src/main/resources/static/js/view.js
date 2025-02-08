const button = document.getElementById("transcript-btn");
const transcript = document.getElementById("transcript");

button.addEventListener("click", function () {
    if (transcript.style.display === "none") {
        transcript.style.display = "block";
        button.textContent = "글 숨기기";
    } else {
        transcript.style.display = "none";
        button.textContent = "글 보이기";
    }
});
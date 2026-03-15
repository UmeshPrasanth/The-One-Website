const playBtn = document.getElementById("play");
const time = document.getElementById("time");
const songs = document.querySelectorAll(".song");
let audio = new Audio();
let currentSong = 0;

function loadSong(index) {
    songs.forEach(s => s.classList.remove("active"));
    songs[index].classList.add("active");
    audio.src = songs[index].dataset.src;
    audio.load();
}

loadSong(currentSong);

playBtn.addEventListener("click", () => {
    if (audio.paused) {
        audio.play();
        playBtn.innerHTML = '<i class="fa fa-pause"></i>';
    } else {
        audio.pause();
        playBtn.innerHTML = '<i class="fa fa-play"></i>';
    }
});

    document.getElementById("prev").addEventListener("click", () => {
      currentSong = (currentSong - 1 + songs.length) % songs.length;
      loadSong(currentSong);
      audio.play();
      playBtn.innerHTML = '<i class="fa fa-pause"></i>';
    });

    document.getElementById("next").addEventListener("click", () => {
      currentSong = (currentSong + 1) % songs.length;
      loadSong(currentSong);
      audio.play();
      playBtn.innerHTML = '<i class="fa fa-pause"></i>';
    });

    audio.addEventListener("timeupdate", () => {
      let current = formatTime(audio.currentTime);
      let duration = formatTime(audio.duration);
      time.textContent = `${current} / ${duration}`;
    });

    function formatTime(sec) {
      if (isNaN(sec)) return "00:00";
      let m = Math.floor(sec / 60);
      let s = Math.floor(sec % 60);
      return `${m < 10 ? "0" + m : m}:${s < 10 ? "0" + s : s}`;
    }

const progressContainer = document.querySelector(".progress-container");
const progress = document.getElementById("progress");

// Update progress bar as audio plays
audio.addEventListener("timeupdate", () => {
    const percent = (audio.currentTime / audio.duration) * 100;
    progress.style.width = `${percent}%`;
    // Update timer as before
    const current = formatTime(audio.currentTime);
    const duration = formatTime(audio.duration);
    timeEl.textContent = `${current} / ${duration}`;
});

// Seek when user clicks on progress bar
progressContainer.addEventListener("click", (e) => {
    const width = progressContainer.clientWidth;
    const clickX = e.offsetX;
    const duration = audio.duration;

    audio.currentTime = (clickX / width) * duration;
});

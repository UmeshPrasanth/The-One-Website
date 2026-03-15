document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll(".head2 a");
  const items = document.querySelectorAll(".video-item");

  buttons.forEach(btn => {
    btn.addEventListener("click", (e) => {
      e.preventDefault();

      buttons.forEach(b => b.classList.remove("active"));
      btn.classList.add("active");

      const filter = btn.getAttribute("data-filter");

      items.forEach(item => {
        if (filter === "all" || item.classList.contains(filter)) {
          item.classList.remove("hide");
        } else {
          item.classList.add("hide");
        }
      });
    });
  });
});

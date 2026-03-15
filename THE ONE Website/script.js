// Fade-In
document.addEventListener("DOMContentLoaded", () => {
  const observer = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add("show");
        observer.unobserve(entry.target);
      }
    });
  }, {
    threshold: 0.2
  });

  document.querySelectorAll(".fade-in").forEach(el => {
    observer.observe(el);
  });
});

// Header
let lastScroll = 0;
const header = document.querySelector("header");
let ticking = false;

window.addEventListener("scroll", () => {
  const currentScroll = window.pageYOffset;

  if (!ticking) {
    window.requestAnimationFrame(() => {
      if (currentScroll > lastScroll) {
        header.style.opacity = "0";
      } else {
        header.style.opacity = "";
        header.style.backgroundColor = "rgba(0,0,0,0.5)";
      }
      if (currentScroll === 0) {
        header.style.backgroundColor = "transparent";
      }

      lastScroll = currentScroll;
      ticking = false;
    });

    ticking = true;
  }
});


function toggleMenu() {
  document.querySelector('.nav-links').classList.toggle('show');
  document.querySelector('.hamburger').classList.toggle('active');
}



// Carousel Img Slider
const items = document.querySelectorAll('.carousel-item');
let currentIndex = 0;

function showSlide(index) {
  items.forEach((item, i) => {
    item.classList.remove('active');
    if (i === index) {
      item.classList.add('active');
    }
  });
}

document.querySelector('.next').addEventListener('click', () => {
  currentIndex = (currentIndex + 1) % items.length;
  showSlide(currentIndex);
});

document.querySelector('.prev').addEventListener('click', () => {
  currentIndex = (currentIndex - 1 + items.length) % items.length;
  showSlide(currentIndex);
});

setInterval(() => {
  currentIndex = (currentIndex + 1) % items.length;
  showSlide(currentIndex);
}, 4000);
window.addEventListener("load", () => {
  showSlide(currentIndex);
});


// Latest-Videos

const albums = document.querySelectorAll('.vd-album');
const nextBtn = document.querySelector('.vd-next');
const prevBtn = document.querySelector('.vd-prev');

let currentIndex1 = 0;

function updateCarousel() {
  albums.forEach((album, i) => {
    album.classList.remove('active', 'side');
    album.style.display = "none";
    if (i === currentIndex1) {
      album.classList.add('active');
      album.style.display = "block";
    } else if (
      i === (currentIndex1 - 1 + albums.length) % albums.length ||
      i === (currentIndex1 + 1) % albums.length
    ) {
      album.classList.add('side');
      album.style.display = "block";
    }
  });
}

nextBtn.addEventListener('click', () => {
  currentIndex1 = (currentIndex1 + 1) % albums.length;
  updateCarousel();
});

prevBtn.addEventListener('click', () => {
  currentIndex1 = (currentIndex1 - 1 + albums.length) % albums.length;
  updateCarousel();
});

updateCarousel();

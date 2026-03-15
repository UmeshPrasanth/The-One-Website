
/*
  Admin dashboard JS
  - Uses backend at http://localhost:8085
  - Endpoints:
    GET  /api/reviews
    GET  /api/reviews/search?query=...
    DELETE /api/reviews/{id}
    PUT    /api/reviews/{id}/toggle-read
  - Login check: requires localStorage.loggedIn === "true"
*/

const API_BASE = "http://localhost:8085/api";
let reviews = [];        // all reviews fetched from server (or search results)
let currentPage = 1;
let perPage = parseInt(document.getElementById('perPage').value,10) || 10;
let totalPages = 1;
let currentQuery = "";

// ------------ login check -------------
function ensureLoggedIn() {
  if (localStorage.getItem('loggedIn') !== 'true') {
    // redirect to login.html (hidden link)
    alert("You must be logged in to view the admin dashboard.");
    window.location.href = 'login.html';
    return false;
  }
  return true;
}

if (!ensureLoggedIn()) {
  // will redirect - stop running further
}

// ------------ fetch utils -------------
async function fetchAllReviews() {
  const res = await fetch(`${API_BASE}/reviews`);
  if (!res.ok) throw new Error('Failed to fetch reviews');
  return res.json();
}
async function fetchSearch(query) {
  const res = await fetch(`${API_BASE}/reviews/search?query=${encodeURIComponent(query)}`);
  if (!res.ok) throw new Error('Search failed');
  return res.json();
}

// ------------ render & pagination -------------
function computePagination() {
  perPage = parseInt(document.getElementById('perPage').value,10) || 10;
  totalPages = Math.max(1, Math.ceil(reviews.length / perPage));
  if (currentPage > totalPages) currentPage = totalPages;
}

function renderTable() {
  const tbody = document.getElementById('reviewTableBody');
  if (!reviews || reviews.length === 0) {
    tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;padding:40px 0;">No reviews found</td></tr>`;
    document.getElementById('pageInfo').textContent = "Showing 0 reviews";
    return;
  }

  computePagination();
  const start = (currentPage - 1) * perPage;
  const pageItems = reviews.slice(start, start + perPage);

  tbody.innerHTML = pageItems.map(r => {
    const commentShort = escapeHtml(r.comment || '').replace(/\n/g,'<br>');
    const readClass = r.readFlag ? 'read' : 'unread';
    const readLabel = r.readFlag ? 'Read' : 'Unread';
    return `
      <tr class="${r.readFlag ? '' : 'unread'}" data-id="${r.id}">
        <td class="small">${r.id}</td>
        <td><strong>${escapeHtml(r.name || '')}</strong></td>
        <td class="small">${escapeHtml(r.email || '')}</td>
        <td>${commentShort}</td>
        <td>
          <div class="actions">
            <span class="pill">${readLabel}</span>
            <button data-id="${r.id}" class="toggle-read ${r.readFlag ? 'read' : ''}" title="Toggle read">${ r.readFlag ? '<i class="fa fa-eye"></i>':'<i class="fa fa-eye-slash"></i>' }</button>
            <button data-id="${r.id}" class="delete-btn" title="Delete review"><i class="fa fa-trash"></i></button>
          </div>
        </td>
      </tr>
    `;
  }).join('');

  // page info text
  const showingFrom = start + 1;
  const showingTo = Math.min(start + perPage, reviews.length);
  document.getElementById('pageInfo').textContent = `Showing ${showingFrom}–${showingTo} of ${reviews.length} reviews • page ${currentPage} / ${totalPages}`;

  // attach event listeners for the rows
  attachRowListeners();
}

function attachRowListeners() {
  document.querySelectorAll('.delete-btn').forEach(btn=>{
    btn.onclick = async (e)=>{
      const id = btn.getAttribute('data-id');
      if (!confirm('Delete this review permanently?')) return;
      try {
        const res = await fetch(`${API_BASE}/reviews/${id}`, { method: 'DELETE' });
        if (!res.ok) throw new Error('Delete failed');
        // optimistic remove from local list
        reviews = reviews.filter(r => String(r.id) !== String(id));
        // simple fade animation: remove row
        const row = btn.closest('tr');
        row.classList.add('row-fade-out');
        setTimeout(() => renderTable(), 260);
      } catch(err) {
        console.error(err);
        alert('Could not delete review. Check the console.');
      }
    };
  });

  document.querySelectorAll('.toggle-read').forEach(btn=>{
    btn.onclick = async (e)=>{
      const id = btn.getAttribute('data-id');
      try {
        const res = await fetch(`${API_BASE}/reviews/${id}/toggle-read`, { method: 'PUT' });
        if (!res.ok) throw new Error('Toggle failed');
        const updated = await res.json();
        // update local copy
        reviews = reviews.map(r => (String(r.id) === String(updated.id) ? updated : r));
        renderTable();
      } catch(err) {
        console.error(err);
        alert('Could not toggle read status.');
      }
    };
  });
}

// ------------ search & load -------------
async function loadAndRender(query = "") {
  try {
    // show loading skeleton
    document.getElementById('reviewTableBody').innerHTML = `<tr><td colspan="5" style="text-align:center;padding:40px 0;">Loading reviews…</td></tr>`;
    if (query && query.trim() !== "") {
      reviews = await fetchSearch(query.trim());
      currentQuery = query.trim();
    } else {
      reviews = await fetchAllReviews();
      currentQuery = "";
    }
    currentPage = 1;
    renderTable();
  } catch (err) {
    console.error(err);
    document.getElementById('reviewTableBody').innerHTML = `<tr><td colspan="5" style="text-align:center;padding:40px 0;">Error loading reviews. Check server.</td></tr>`;
    document.getElementById('pageInfo').textContent = "Error loading";
  }
}

// ------------ helpers -------------
function escapeHtml(s) {
  if (!s) return '';
  return s.replace(/[&<>"']/g, function(m){ return ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#039;'})[m]; });
}

// ------------ UI bindings -------------
document.getElementById('searchBtn').addEventListener('click', ()=>{
  const q = document.getElementById('searchInput').value;
  loadAndRender(q);
});
document.getElementById('clearBtn').addEventListener('click', ()=>{
  document.getElementById('searchInput').value = '';
  loadAndRender('');
});
document.getElementById('searchInput').addEventListener('keydown', (e)=>{
  if (e.key === 'Enter') { loadAndRender(document.getElementById('searchInput').value); }
});
document.getElementById('perPage').addEventListener('change', ()=>{
  perPage = parseInt(document.getElementById('perPage').value,10);
  currentPage = 1;
  renderTable();
});
document.getElementById('prevBtn').addEventListener('click', ()=>{
  if (currentPage > 1) { currentPage--; renderTable(); }
});
document.getElementById('nextBtn').addEventListener('click', ()=>{
  if (currentPage < totalPages) { currentPage++; renderTable(); }
});
document.getElementById('logoutBtn').addEventListener('click', ()=>{
  if (confirm('Logout from admin?')) {
    localStorage.removeItem('loggedIn');
    window.location.href = 'login.html';
  }
});

// initial load
loadAndRender(''); // fetch all reviews on page open
const CART_KEY = "sc_cart_v1";

function formatPrice(n) {
  return "$" + String(n).replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

function loadCart() {
  const raw = localStorage.getItem(CART_KEY);
  return raw ? JSON.parse(raw) : {};
}

function saveCart(cart) {
  localStorage.setItem(CART_KEY, JSON.stringify(cart));
  updateCartBadge();
}

function updateCartBadge() {
  const cart = loadCart();
  const count = Object.values(cart).reduce((s, i) => s + i.qty, 0);
  const badges = document.querySelectorAll("#cart-badge");
  badges.forEach(b => b.textContent = count);
}

function showToast(msg) {
  let t = document.createElement("div");
  t.style = "position:fixed;right:20px;bottom:20px;background:#198754;padding:12px 16px;border-radius:8px;color:white;z-index:9999;opacity:0;transition:opacity .3s; font-weight: bold;";
  t.innerHTML = `<i class="bi bi-cart-check-fill me-2"></i>${msg}`;
  document.body.appendChild(t);
  requestAnimationFrame(() => (t.style.opacity = 1));
  setTimeout(() => {
    t.style.opacity = 0;
    setTimeout(() => t.remove(), 300);
  }, 1800);
}

document.addEventListener("DOMContentLoaded", () => {
    updateCartBadge();

    // Listeners for 'Añadir' buttons in catalog
    document.querySelectorAll(".add-cart-btn").forEach(btn => {
        btn.addEventListener("click", e => {
            e.preventDefault();
            const id = btn.getAttribute("data-id");
            const name = btn.getAttribute("data-name");
            const price = parseFloat(btn.getAttribute("data-price"));
            const img = btn.getAttribute("data-img");
            
            const cart = loadCart();
            if(!cart[id]) {
                cart[id] = { id, name, price, img, qty: 0 };
            }
            cart[id].qty += 1;
            saveCart(cart);
            showToast("Agregado al carrito");
        });
    });

    // Render cart if on cart page
    const container = document.getElementById("cartContainer");
    if(container) {
        renderCartPage();
    }
});

function renderCartPage() {
    const container = document.getElementById("cartContainer");
    if(!container) return;
    
    const cart = loadCart();
    const ids = Object.keys(cart);
    container.innerHTML = "";
    
    if(ids.length === 0) {
        container.innerHTML = `
        <div class="text-center py-5">
            <i class="bi bi-cart-x display-1 text-muted mb-3"></i>
            <h4 class="text-white-50">Parece que aún no has agregado nada a tu carrito.</h4>
            <div class="mt-4">
                <a href="/catalogo" class="btn btn-warning btn-lg fw-bold">Volver al Catálogo</a>
            </div>
        </div>`;
        const summary = document.getElementById("cart-summary");
        if(summary) summary.style.display = 'none';
        return;
    }
    
    const summary = document.getElementById("cart-summary");
    if(summary) summary.style.display = 'block';

    ids.forEach(id => {
        const item = cart[id];
        const row = document.createElement("div");
        row.className = "d-flex align-items-center mb-3 bg-dark bg-opacity-50 p-3 rounded border border-secondary border-opacity-25";
        row.innerHTML = `
        <img src="${item.img || '/image/cafeVerdeSA.png'}" style="width:84px;height:84px;object-fit:cover;border-radius:8px;margin-right:15px">
        <div class="flex-grow-1">
            <div class="d-flex justify-content-between align-items-start">
            <div>
                <h6 class="mb-1 text-white fw-bold">${item.name}</h6>
            </div>
            <div class="text-end">
                <div class="text-success fw-bold fs-5">${formatPrice(item.price * item.qty)}</div>
                <small class="text-white-50">Unit ${formatPrice(item.price)}</small>
            </div>
            </div>
            <div class="mt-2 d-flex align-items-center">
            <button class="btn btn-sm btn-outline-light me-2 minus-btn fw-bold px-2" data-id="${id}">-</button>
            <span class="px-3 text-white fw-bold">${item.qty}</span>
            <button class="btn btn-sm btn-outline-light ms-2 plus-btn fw-bold px-2" data-id="${id}">+</button>
            <button class="btn btn-sm btn-danger ms-4 remove-btn" data-id="${id}"><i class="bi bi-trash"></i> Eliminar</button>
            </div>
        </div>
        `;
        container.appendChild(row);
    });

    document.querySelectorAll(".plus-btn").forEach(b => b.addEventListener("click", e => modifyQty(e.currentTarget.dataset.id, 1)));
    document.querySelectorAll(".minus-btn").forEach(b => b.addEventListener("click", e => modifyQty(e.currentTarget.dataset.id, -1)));
    document.querySelectorAll(".remove-btn").forEach(b => b.addEventListener("click", e => removeItem(e.currentTarget.dataset.id)));
    
    updateCartTotal();
}

function modifyQty(id, delta) {
    const cart = loadCart();
    if(!cart[id]) return;
    cart[id].qty += delta;
    if(cart[id].qty <= 0) delete cart[id];
    saveCart(cart);
    renderCartPage();
}

function removeItem(id) {
    const cart = loadCart();
    if(cart[id]) delete cart[id];
    saveCart(cart);
    renderCartPage();
}

function updateCartTotal() {
    const totalEl = document.getElementById("cart-total");
    if(!totalEl) return;
    const cart = loadCart();
    const total = Object.values(cart).reduce((s, item) => s + (item.price * item.qty), 0);
    totalEl.textContent = formatPrice(total);
}

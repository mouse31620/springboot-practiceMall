/*!
* Start Bootstrap - Shop Homepage v5.0.6 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function updateCartDisplay() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const cartCount = document.getElementById('cart-count');
    const cartItems = document.getElementById('cart-items');
    const totalPriceEl = document.getElementById('total-price');

    cartItems.innerHTML = ''; // 清空現有的內容
    let totalPrice = 0;

    if (cart.length > 0) {
      cart.forEach((item, index) => {
      const itemTotal = item.price * item.quantity;
      totalPrice += itemTotal;
      const cartItemHTML = `
        <li class="dropdown-item d-flex justify-content-between align-items-center">
          <div>
            <strong>${item.name}</strong><br>
            Price: $${item.price} x ${item.quantity}
          </div>
          <div>
            <button class="btn btn-sm btn-danger me-1" onclick="removeFromCart(${index})">移除</button>
            <button class="btn btn-sm btn-outline-primary" onclick="increaseQuantity(${index})">+</button>
            <button class="btn btn-sm btn-outline-secondary" onclick="decreaseQuantity(${index})">-</button>
          </div>
        </li>`;
      cartItems.innerHTML += cartItemHTML;
      });
    } else {
      cartItems.innerHTML = '<p class="dropdown-item">購物車目前是空的</p>';
    }

    cartCount.textContent = cart.length;
    totalPriceEl.textContent = totalPrice.toFixed(2);
}

function addToCart(productId) {
  // 假設 /api/check-stock 是用來檢查商品庫存的 API
  fetch(`/products/stock/${productId}`)
    .then(response => response.json())
    .then(data => {
      if (data.stock > 0) {
        // 如果庫存充足，將商品加入購物車
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        const product = products.find(p => p.id === productId);
        const existingProduct = cart.find(item => item.id === product.id);

        if (existingProduct) {
          existingProduct.quantity += 1;
        } else {
          cart.push({ ...product, quantity: 1 });
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        updateCartDisplay();
        alert(`${product.name} 已加入購物車！`);
      } else {
        alert('該商品已售罄');
      }
    })
    .catch(error => console.error('Error checking stock:', error));
}

// 增加商品數量
function increaseQuantity(productId) {
  let cart = JSON.parse(localStorage.getItem('cart')) || [];
  const productIndex = cart.findIndex(item => item.id === productId);

  if (productIndex !== -1) {
    // 假設 /api/check-stock 是用來檢查庫存的 API
    fetch(`/api/check-stock/${productId}`)
      .then(response => response.json())
      .then(data => {
        if (cart[productIndex].quantity < data.stock) {
          cart[productIndex].quantity += 1; // 增加數量
          localStorage.setItem('cart', JSON.stringify(cart)); // 更新 localStorage
          updateCartDisplay(); // 更新購物車顯示
        } else {
          alert('庫存不足，無法增加商品數量');
        }
      })
      .catch(error => console.error('Error checking stock:', error));
  }
}

// 減少商品數量
function decreaseQuantity(productId) {
  let cart = JSON.parse(localStorage.getItem('cart')) || [];
  const productIndex = cart.findIndex(item => item.id === productId);

  if (productIndex !== -1) {
    if (cart[productIndex].quantity > 1) {
      cart[productIndex].quantity -= 1; // 減少數量
      localStorage.setItem('cart', JSON.stringify(cart)); // 更新 localStorage
      updateCartDisplay(); // 更新購物車顯示
    } else {
      removeFromCart(productId); // 如果數量為 1，則直接移除
    }
  }
}

// 移除商品
function removeFromCart(productId) {
  let cart = JSON.parse(localStorage.getItem('cart')) || [];
  cart = cart.filter(item => item.id !== productId); // 過濾掉要移除的商品
  localStorage.setItem('cart', JSON.stringify(cart)); // 更新 localStorage
  updateCartDisplay(); // 更新購物車顯示
}

function showItem(itemName) {
    document.getElementById(itemName).style.display = 'block';
}
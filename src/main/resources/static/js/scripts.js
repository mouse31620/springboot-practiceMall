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

// 定義一個函數來獲取商品庫存數量
async function getProductStock(productId) {
    try {
        const response = await fetch(`/products/stock/${productId}`);
        const stock = await response.json();
        return stock;
    } catch (error) {
        console.error('Error checking stock:', error);
        return 0; // 如果出現錯誤，返回庫存 0
    }
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
            <tr>
              <td><strong>${item.productName}</strong></td>
              <td>$${item.price}</td>
              <td class="text-center">
                <div class="d-flex justify-content-center align-items-center">
                  <button class="btn btn-sm btn-outline-secondary" onclick="decreaseQuantity(event, ${item.id})">-</button>
                  <input type="text" id="quantity-${item.id}" class="form-control mx-1 text-center" value="${item.quantity}" onchange="checkQuantity(event, ${item.id}, this.value)" style="width: 60px;">
                  <button class="btn btn-sm btn-outline-primary" onclick="increaseQuantity(event, ${item.id})">+</button>
                </div>
              </td>
              <td>$${itemTotal.toFixed(2)}</td>
              <td>
                <button class="btn btn-sm btn-danger" onclick="removeFromCart(event, ${item.id})">移除</button>
              </td>
            </tr>`;
      cartItems.innerHTML += cartItemHTML;
      });
    } else {
        cartItems.innerHTML = '<tr><td colspan="5">購物車目前是空的</td></tr>'
    }

    cartCount.textContent = cart.length;
    totalPriceEl.textContent = totalPrice.toFixed(2);
}

async function checkQuantity(event, productId, newQuantity) {
    event.preventDefault();
    event.stopPropagation(); // 阻止事件冒泡
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    const productIndex = cart.findIndex(item => item.id === productId);

    const stock = await getProductStock(productId);
    newQuantity = parseInt(newQuantity, 10);
    if (newQuantity <= stock && newQuantity >= 1) {
        cart[productIndex].quantity = newQuantity;
        localStorage.setItem('cart', JSON.stringify(cart)); // 更新localStorage
    } else {
        alert('數量超過庫存或小於1');
        document.getElementById(`quantity-${productId}`).value = cart[productIndex].quantity; // 重設為原來的數量
    }
}

async function addToCart(productId) {
    const stock = await getProductStock(productId);
    if (stock > 0) {
        // 如果庫存充足，將商品加入購物車
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        const product = productList.find(p => p.id === productId);
        const existingProduct = cart.find(item => item.id === product.id);

        if (existingProduct) {
          existingProduct.quantity += 1;
        } else {
          cart.push({ ...product, quantity: 1 });
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        updateCartDisplay();
        alert(`${product.productName} 已加入購物車！`);
    } else {
        alert('該商品已售罄');
    }
}

// 增加商品數量
async function increaseQuantity(event, productId) {
    event.stopPropagation(); // 阻止事件冒泡
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    const productIndex = cart.findIndex(item => item.id === productId);

    if (productIndex !== -1) {
    const stock = await getProductStock(productId);
    if (cart[productIndex].quantity < stock) {
      cart[productIndex].quantity += 1; // 增加數量
      localStorage.setItem('cart', JSON.stringify(cart)); // 更新 localStorage
      let count = document.getElementById(`quantity-${productId}`).value;
      count = parseInt(count, 10) + 1;
      document.getElementById(`quantity-${productId}`).value = count;
      //updateCartDisplay(); // 更新購物車顯示
    } else {
      alert('庫存不足，無法增加商品數量');
    }
    }
}

// 減少商品數量
function decreaseQuantity(event, productId) {
    event.stopPropagation(); // 阻止事件冒泡
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    const productIndex = cart.findIndex(item => item.id === productId);

    if (productIndex !== -1) {
    if (cart[productIndex].quantity > 1) {
      cart[productIndex].quantity -= 1; // 減少數量
      localStorage.setItem('cart', JSON.stringify(cart)); // 更新 localStorage
      let count = document.getElementById(`quantity-${productId}`).value;
      count = parseInt(count, 10) - 1;
      document.getElementById(`quantity-${productId}`).value = count;
    } else {
      removeFromCart(productId); // 如果數量為 1，則直接移除
    }
    }
}

// 移除商品
function removeFromCart(event, productId) {
    event.stopPropagation(); // 阻止事件冒泡
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart = cart.filter(item => item.id !== productId); // 過濾掉要移除的商品
    localStorage.setItem('cart', JSON.stringify(cart)); // 更新 localStorage
    updateCartDisplay(); // 更新購物車顯示
}

function showItem(itemName) {
    document.getElementById(itemName).style.display = 'block';
}
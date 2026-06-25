<script setup lang="ts">
import { computed, ref } from 'vue'

type Product = {
  id: number
  name: string
  price: number
  stock: number
}

type CartItem = Product & {
  quantity: number
}

const products = ref<Product[]>([
  { id: 1, name: 'Coca Cola', price: 1.99, stock: 20 },
  { id: 2, name: 'Fanta', price: 1.89, stock: 18 },
  { id: 3, name: 'Sprite', price: 1.89, stock: 15 },
  { id: 4, name: 'Red Bull', price: 2.49, stock: 12 },
  { id: 5, name: 'Snickers', price: 1.29, stock: 25 },
  { id: 6, name: 'Twix', price: 1.39, stock: 20 },
  { id: 7, name: 'Pringles Paprika', price: 2.99, stock: 10 },
  { id: 8, name: 'Wasser Still', price: 0.89, stock: 30 },
])

const cart = ref<CartItem[]>([])
const orderMessage = ref('')

const cartTotal = computed(() =>
  cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0),
)

function addToCart(product: Product) {
  const item = cart.value.find((p) => p.id === product.id)

  if (item) {
    item.quantity++
  } else {
    cart.value.push({ ...product, quantity: 1 })
  }

  orderMessage.value = ''
}

function removeFromCart(productId: number) {
  cart.value = cart.value.filter((item) => item.id !== productId)
}

function placeOrder() {
  if (cart.value.length === 0) {
    orderMessage.value = 'Der Warenkorb ist leer.'
    return
  }

  orderMessage.value = `Bestellung erfolgreich! Gesamtbetrag: ${cartTotal.value.toFixed(2)} €`
  cart.value = []
}
</script>

<template>
  <main class="page">
    <header class="header">
      <div>
        <h1>FlowGraph Shop</h1>
      </div>

      <div class="cart-pill">
        Warenkorb: {{ cart.length }}
      </div>
    </header>

    <section class="layout">
      <div class="products">
        <h2>Produkte</h2>

        <div class="product-grid">
          <article v-for="product in products" :key="product.id" class="card">

            <h3>{{ product.name }}</h3>
            <p>Bestand: {{ product.stock }}</p>

            <div class="card-footer">
              <strong>{{ product.price.toFixed(2) }} €</strong>
              <button @click="addToCart(product)">Hinzufügen</button>
            </div>
          </article>
        </div>
      </div>

      <aside class="cart">
        <h2>Warenkorb</h2>

        <div v-if="cart.length === 0" class="empty">
          Noch keine Produkte im Warenkorb.
        </div>

        <div v-else class="cart-list">
          <div v-for="item in cart" :key="item.id" class="cart-item">
            <div>
              <strong>{{ item.name }}</strong>
              <p>{{ item.quantity }} × {{ item.price.toFixed(2) }} €</p>
            </div>

            <button class="remove" @click="removeFromCart(item.id)">×</button>
          </div>
        </div>

        <div class="total">
          <span>Gesamt</span>
          <strong>{{ cartTotal.toFixed(2) }} €</strong>
        </div>

        <button class="order" @click="placeOrder">Bestellen</button>

        <p v-if="orderMessage" class="message">
          {{ orderMessage }}
        </p>
      </aside>
    </section>
  </main>
</template>

<style scoped>
.page {
  min-height: 100vh;
  padding: 40px;
  background: #f4f6f8;
  color: #17242b;
  font-family: Inter, Arial, sans-serif;
}

.header {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  align-items: center;
  margin-bottom: 32px;
}

.eyebrow {
  text-transform: uppercase;
  letter-spacing: 0.12em;
  font-size: 13px;
  color: #64748b;
  margin: 0 0 8px;
}

h1 {
  font-size: 48px;
  margin: 0;
}

.subtitle {
  color: #64748b;
  margin-top: 8px;
}

.cart-pill {
  background: #17242b;
  color: white;
  padding: 14px 20px;
  border-radius: 999px;
  font-weight: 700;
}

.layout {
  display: grid;
  grid-template-columns: 1fr 360px;
  gap: 28px;
}

.products,
.cart {
  background: white;
  border-radius: 22px;
  padding: 26px;
  box-shadow: 0 18px 40px rgba(15, 23, 42, 0.08);
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(160px, 1fr));
  gap: 18px;
}

.card {
  border: 1px solid #e5e7eb;
  border-radius: 18px;
  padding: 18px;
  background: #ffffff;
}

.product-image {
  height: 100px;
  border-radius: 16px;
  background: linear-gradient(135deg, #dbeafe, #e0f2fe);
  display: grid;
  place-items: center;
  font-size: 40px;
  font-weight: 800;
  margin-bottom: 16px;
}

.card h3 {
  margin: 0 0 6px;
}

.card p {
  margin: 0 0 16px;
  color: #64748b;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

button {
  border: none;
  background: #2563eb;
  color: white;
  padding: 10px 14px;
  border-radius: 12px;
  cursor: pointer;
  font-weight: 700;
}

button:hover {
  background: #1d4ed8;
}

.empty {
  color: #64748b;
  padding: 20px 0;
}

.cart-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  border-bottom: 1px solid #e5e7eb;
  padding-bottom: 12px;
}

.cart-item p {
  margin: 4px 0 0;
  color: #64748b;
}

.remove {
  background: #ef4444;
  border-radius: 50%;
  width: 34px;
  height: 34px;
  padding: 0;
}

.remove:hover {
  background: #dc2626;
}

.total {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  font-size: 20px;
}

.order {
  width: 100%;
  margin-top: 18px;
  background: #16a34a;
  padding: 14px;
}

.order:hover {
  background: #15803d;
}

.message {
  margin-top: 16px;
  background: #dcfce7;
  color: #166534;
  padding: 12px;
  border-radius: 12px;
  font-weight: 600;
}

@media (max-width: 1000px) {
  .layout {
    grid-template-columns: 1fr;
  }

  .product-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>

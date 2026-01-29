<template>
  <div class="admin-container">
    <aside class="sidebar">
      <div class="brand">
        <span class="logo">✂</span>
        <h2>Painel ASVUM</h2>
      </div>
      <nav class="menu">
        <router-link to="/admin" class="nav-item">
          <i class="pi pi-users"></i> Usuários
        </router-link>
        <router-link to="/admin/servicos" class="nav-item">
          <i class="pi pi-list"></i> Serviços
        </router-link>
        <router-link to="/admin/barbeiros" class="nav-item">
          <i class="pi pi-id-card"></i> Barbeiros
        </router-link>
        <div class="divider"></div>
        <router-link to="/" class="nav-item back-site">
          <i class="pi pi-external-link"></i> Ver Site
        </router-link>
      </nav>
    </aside>

    <main class="admin-content">
      <div class="glass-card">
        <router-view />
      </div>
    </main>
  </div>
</template>

<style scoped>
.admin-container {
  display: flex;
  min-height: 100vh;
  background: #050505;
}

.sidebar {
  width: 260px;
  background: rgba(15, 15, 15, 0.9);
  border-right: 1px solid rgba(212, 175, 55, 0.2);
  padding: 2rem 1rem;
  display: flex;
  flex-direction: column;
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
  padding-bottom: 2rem;
  color: #d4af37;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 15px;
  color: #ccc;
  text-decoration: none;
  border-radius: 8px;
  margin-bottom: 0.5rem;
  transition: 0.3s;
}

.nav-item:hover, .router-link-exact-active {
  background: rgba(212, 175, 55, 0.1);
  color: #d4af37;
}

.admin-content {
  flex: 1;
  padding: 2rem;
  overflow-y: auto;
}

.glass-card {
  background: rgba(20, 20, 20, 0.6);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 15px;
  padding: 1.5rem;
}

.divider {
  height: 1px;
  background: rgba(212, 175, 55, 0.1);
  margin: 1rem 0;
}
</style>
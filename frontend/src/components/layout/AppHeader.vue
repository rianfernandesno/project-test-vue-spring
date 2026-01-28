<template>
  <header class="header-fixed">
    <Menubar :model="items" class="custom-menubar">
      <template #start>
        <div class="brand-wrapper">
          <span class="brand-logo">✂</span>
          <span class="brand-name">ASVUM</span>
        </div>
      </template>

      <template #item="{ item, props }">
        <a v-ripple class="menu-item" v-bind="props.action">
          <span :class="item.icon" class="menu-icon" />
          <span class="menu-label">{{ item.label }}</span>
        </a>
      </template>

      <template #end>
        <div class="user-actions">
          <Button
            icon="pi pi-user"
            severity="primary"
            rounded
            outlined
            class="profile-btn"
            @click="toggleProfileMenu"
          />
          <Menu ref="profileMenu" :model="profileItems" :popup="true" />
        </div>
      </template>
    </Menubar>
  </header>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const profileMenu = ref();

const items = ref([
  {
    label: "Home",
    icon: "pi pi-home",
    command: () => {
      router.push("/");
    }, // Volta para Welcome
  },
  {
    label: "Serviços",
    icon: "pi pi-list",
    command: () => {
      router.push("/servicos");
    }, // Vai para Serviços
  },
  {
    label: "Barbeiros",
    icon: "pi pi-users",
    command: () => {
      router.push("/barbeiros");
    },
  },
  { label: "Agendar", icon: "pi pi-calendar-plus" },
]);

const profileItems = ref([
  { label: "Meu Perfil", icon: "pi pi-user" },
  { separator: true },
  { label: "Sair", icon: "pi pi-sign-out", class: "logout-item" },
]);

const toggleProfileMenu = (event: Event) => {
  profileMenu.value.toggle(event);
};
</script>

<style scoped>
.header-fixed {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 9999;
  padding: 1rem 2rem;
}

.custom-menubar {
  background: rgba(10, 10, 10, 0.75) !important;
  backdrop-filter: blur(15px);
  border: 1px solid rgba(212, 175, 55, 0.2) !important;
  border-radius: 100px;
}

.brand-wrapper {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  margin-right: 2.5rem;
}

.brand-logo {
  font-size: 1.8rem;
  color: #d4af37; /* Dourado barbearia */
}

.brand-name {
  font-size: 1.6rem;
  font-weight: 900;
  letter-spacing: 2px;
  color: #fff;
  font-family: "serif"; /* Ou uma fonte que você importar */
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 0.75rem 1rem;
  color: #ccc !important;
  transition: all 0.3s;
}

.menu-item:hover {
  color: #d4af37 !important;
  transform: translateY(-2px);
}

.menu-icon {
  margin-right: 0.5rem;
}

.profile-btn {
  border-color: #d4af37 !important;
  color: #d4af37 !important;
}

.profile-btn:hover {
  background: rgba(212, 175, 55, 0.1) !important;
}
</style>

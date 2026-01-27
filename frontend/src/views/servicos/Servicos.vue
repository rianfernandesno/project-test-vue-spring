<template>
  <div class="servicos-container">
    <div class="header-section">
      <h1>Nossos Serviços</h1>
      <p>Escolha o melhor para o seu visual</p>
    </div>

    <div v-if="loading" class="loading-state">
      <i class="pi pi-spin pi-spinner" style="font-size: 2rem"></i>
    </div>

    <div v-else-if="servicos.length > 0" class="grid-layout">
      <div v-for="servico in servicos" :key="servico.id" class="card-wrapper">
        <div class="servico-card">
          <div class="card-header">
            <span class="badge">{{ servico.especialidade }}</span>
            <h2 class="nome-servico">{{ servico.nome }}</h2>
          </div>
          
          <div class="card-body">
            <p class="descricao">{{ servico.descricao }}</p>
            <div class="detalhes">
              <span class="duracao">
                <i class="pi pi-clock"></i> {{ servico.duracao }} min
              </span>
              <span class="preco">
                R$ {{ servico.preco.toFixed(2) }}
              </span>
            </div>
          </div>
          
          <div class="card-footer">
            <Button label="Agendar" icon="pi pi-calendar" class="p-button-gold" />
          </div>
        </div>
      </div>
    </div>

    <div v-else class="empty-state">
      <p>Nenhum serviço encontrado no momento.</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import servicoService from "../../services/servicoService.ts";

const servicos = ref<Servico[]>([]);
const loading = ref(true);

const fetchServicos = async () => {
  try {
    servicos.value = await servicoService.getServicos();
  } catch (error) {
    console.error("Erro ao buscar serviços:", error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchServicos();
});
</script>

<style scoped>
.servicos-container {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.header-section {
  text-align: center;
  margin-bottom: 3rem;
}

.header-section h1 {
  font-size: 2.5rem;
  color: var(--primary-color);
  text-transform: uppercase;
  letter-spacing: 2px;
}

.grid-layout {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.servico-card {
  background: #1a1a1a;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  transition: transform 0.3s ease, border-color 0.3s ease;
}

.servico-card:hover {
  transform: translateY(-5px);
  border-color: var(--primary-color);
}

.badge {
  background: #333;
  color: var(--primary-color);
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: bold;
}

.nome-servico {
  margin: 1rem 0;
  font-size: 1.5rem;
}

.descricao {
  color: #aaa;
  font-size: 0.9rem;
  line-height: 1.4;
  height: 3rem;
  overflow: hidden;
}

.detalhes {
  margin: 1.5rem 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.preco {
  font-size: 1.4rem;
  font-weight: bold;
  color: var(--primary-color);
}

.duracao {
  color: #888;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.p-button-gold {
  background-color: var(--primary-color) !important;
  border-color: var(--primary-color) !important;
  color: #000 !important;
  width: 100%;
}

.loading-state, .empty-state {
  text-align: center;
  margin-top: 5rem;
}
</style>
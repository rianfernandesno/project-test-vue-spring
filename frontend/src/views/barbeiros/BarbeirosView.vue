<script setup lang="ts">
import { ref, onMounted } from 'vue';
import barbeiroService, { type Barbeiro } from '../../services/barbeiroService';
import Dialog from 'primevue/dialog';

const barbeiros = ref<Barbeiro[]>([]);
const loading = ref(true);
const displayModal = ref(false);
const selectedBarbeiro = ref<Barbeiro | null>(null);

const diasTraduzidos: Record<string, string> = {
  'MONDAY': 'Segunda-feira',
  'TUESDAY': 'Terça-feira',
  'WEDNESDAY': 'Quarta-feira',
  'THURSDAY': 'Quinta-feira',
  'FRIDAY': 'Sexta-feira',
  'SATURDAY': 'Sábado',
  'SUNDAY': 'Domingo'
};

onMounted(async () => {
  try {
    barbeiros.value = await barbeiroService.getBarbeiros();
  } catch (error) {
    console.error("Erro ao carregar barbeiros:", error);
  } finally {
    loading.value = false;
  }
});

const openHorarios = (barbeiro: Barbeiro) => {
  selectedBarbeiro.value = barbeiro;
  displayModal.value = true;
};

const formatEspecialidade = (esp: string) => {
  return esp.replace(/_/g, ' ').toLowerCase();
};

const traduzirDia = (dia: string) => {
  return diasTraduzidos[dia.toUpperCase()] || dia;
};
</script>

<template>
  <div class="barbeiros-page">
    <div class="content-wrapper">
      <header class="page-header">
        <span class="pre-title">Nossos Profissionais</span>
        <h1 class="title">Equipe <span class="gold">Elite</span></h1>
        <div class="divider-container">
          <div class="line"></div>
          <i class="pi pi-star-fill gold"></i>
          <div class="line"></div>
        </div>
      </header>

      <div v-if="loading" class="loading-container">
        <i class="pi pi-spin pi-spinner" style="font-size: 3rem; color: var(--primary-color)"></i>
      </div>

      <div v-else class="barbeiros-grid">
        <div 
          v-for="barbeiro in barbeiros" 
          :key="barbeiro.id" 
          class="barbeiro-card"
          :class="{ 'is-unavailable': !barbeiro.estaAtivo }"
        >
          <div class="profile-image-wrapper">
            <img :src="barbeiro.fotoPath" :alt="barbeiro.nome" class="profile-image" />
            
            <div v-if="barbeiro.estaAtivo" class="status-badge active-vibrant">
              <i class="pi pi-bolt"></i> DISPONÍVEL
            </div>
            <div v-else class="status-badge status-unavailable">
              <i class="pi pi-moon"></i> OFF-LINE
            </div>
            
            <div class="overlay-gradient"></div>
          </div>

          <div class="barbeiro-info">
            <h2 class="barbeiro-name">{{ barbeiro.nome }}</h2>
            
            <div class="specialties-container">
              <span v-for="esp in barbeiro.especialidades" :key="esp" class="specialty-tag">
                {{ formatEspecialidade(esp) }}
              </span>
            </div>

            <Button 
              :label="barbeiro.estaAtivo ? 'VER AGENDA' : 'INDISPONÍVEL'" 
              :icon="barbeiro.estaAtivo ? 'pi pi-calendar-clock' : 'pi pi-lock'" 
              class="appointment-btn"
              :class="{ 'btn-inactive': !barbeiro.estaAtivo }"
              @click="openHorarios(barbeiro)"
            />
          </div>
        </div>
      </div>
    </div>

    <Dialog 
      v-model:visible="displayModal" 
      modal 
      :header="'Agenda: ' + selectedBarbeiro?.nome" 
      :style="{ width: '90vw', maxWidth: '420px' }"
      class="custom-dialog"
    >
      <div v-if="selectedBarbeiro?.horariosTrabalho?.length" class="horarios-list">
        <div v-for="horario in selectedBarbeiro.horariosTrabalho" :key="horario.id" class="horario-item">
          <span class="dia-semana">{{ traduzirDia(horario.diaDaSemana) }}</span>
          <div class="horas-badge">
            {{ horario.horaInicio.substring(0,5) }} — {{ horario.horaFim.substring(0,5) }}
          </div>
        </div>
      </div>
      <div v-else class="no-data">Nenhum horário cadastrado.</div>
    </Dialog>
  </div>
</template>

<style scoped>
.barbeiros-page {
  padding: 120px 20px 60px;
  background-color: #080808;
  min-height: 100vh;
}

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto;
}

/* Header Centralizado */
.page-header {
  text-align: center;
  margin-bottom: 80px;
}

.pre-title {
  color: #666;
  text-transform: uppercase;
  letter-spacing: 4px;
  font-size: 0.8rem;
  display: block;
  margin-bottom: 10px;
}

.title {
  font-size: clamp(2.5rem, 6vw, 4rem);
  font-weight: 900;
  margin: 0;
  color: #fff;
  text-transform: uppercase;
}

.gold { color: var(--primary-color); }

.divider-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  margin-top: 15px;
}

.line { width: 60px; height: 1px; background: rgba(212, 175, 55, 0.3); }

/* Grid */
.barbeiros-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 40px;
}

/* Card Visual */
.barbeiro-card {
  background: #111;
  border-radius: 24px;
  border: 1px solid #222;
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
}

.barbeiro-card:hover:not(.is-unavailable) {
  transform: translateY(-12px);
  border-color: var(--primary-color);
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.7);
}

/* Estilo para Inativo (Menos luto, mais moderno) */
.is-unavailable {
  border-color: #1a1a1a;
  opacity: 0.85;
}

.is-unavailable .profile-image {
  filter: saturate(0.4) brightness(0.6);
}

.profile-image-wrapper {
  height: 420px;
  position: relative;
  overflow: hidden;
}

.profile-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.6s ease;
}

.barbeiro-card:hover .profile-image {
  transform: scale(1.05);
}

.overlay-gradient {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 50%;
  background: linear-gradient(transparent, #111);
}

/* Badges */
.status-badge {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 2;
  padding: 8px 16px;
  border-radius: 12px;
  font-size: 0.7rem;
  font-weight: 800;
  letter-spacing: 1px;
  backdrop-filter: blur(4px);
}

.active-vibrant {
  background: rgba(0, 255, 136, 0.15);
  color: #00ff88;
  border: 1px solid rgba(0, 255, 136, 0.5);
  box-shadow: 0 0 20px rgba(0, 255, 136, 0.2);
}

.status-unavailable {
  background: rgba(255, 255, 255, 0.05);
  color: #888;
  border: 1px solid #333;
}

.barbeiro-info {
  padding: 0 25px 30px;
  margin-top: -30px;
  position: relative;
  text-align: center;
}

.barbeiro-name {
  font-size: 1.8rem;
  font-weight: 800;
  color: #fff;
  margin-bottom: 12px;
  text-shadow: 0 2px 10px rgba(0,0,0,0.5);
}

.specialties-container {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: 25px;
  min-height: 50px;
}

.specialty-tag {
  background: #1a1a1a;
  padding: 5px 12px;
  border-radius: 6px;
  font-size: 0.7rem;
  color: #999;
  border: 1px solid #222;
  text-transform: capitalize;
}

.appointment-btn {
  width: 100%;
  padding: 12px !important;
  background: var(--primary-color) !important;
  color: #000 !important;
  border: none !important;
  font-weight: 900 !important;
  border-radius: 12px !important;
  transition: transform 0.2s ease !important;
}

.appointment-btn:hover:not(:disabled) {
  transform: scale(1.02);
  filter: brightness(1.1);
}

.btn-inactive {
  background: #222 !important;
  color: #555 !important;
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 400px;
}

/* Modal Estilo */
.horario-item {
  display: flex;
  justify-content: space-between;
  padding: 15px;
  background: #181818;
  border-radius: 12px;
  margin-bottom: 8px;
  border: 1px solid #222;
}

.dia-semana {
  font-weight: 700;
  color: var(--primary-color);
  text-transform: capitalize;
}

.horas-badge {
  color: #fff;
  font-family: 'JetBrains Mono', monospace;
  font-size: 0.9rem;
}
</style>
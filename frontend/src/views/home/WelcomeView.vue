<template>
  <div class="hero-section">
    <div class="overlay"></div>
    
    <div class="content-container">
      <section class="hero-text">
        <span class="tagline">TRADIÇÃO & ESTILO</span>
        <h1 class="title">ASVUM <br><span class="highlight">BARBERSHOP</span></h1>
        <p class="description">
          Mais que um corte, uma experiência de elite. 
          Venha cuidar do seu visual com quem entende de verdade.
        </p>
        
        <div class="action-buttons">
          <Button label="AGENDAR AGORA" icon="pi pi-calendar" class="btn-primary" raised />
          <Button label="NOSSOS SERVIÇOS" icon="pi pi-angle-down" class="btn-secondary" text />
        </div>
      </section>
    </div>

    <div class="bottom-gradient"></div>
  </div>
</template>

<style scoped>
.hero-section {
  position: relative;
  height: 100vh;
  width: 100%;
  display: flex;
  align-items: center;
  overflow: hidden;
  background: url('../../assets/barbershop-welcome-image.jpg') center/cover no-repeat;

}

.overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to right, rgba(0,0,0,0.9) 30%, rgba(0,0,0,0.4) 100%);
}

.content-container {
  position: relative;
  z-index: 2;
  padding-left: 10%;
  width: 100%;
}

.hero-text {
  max-width: 700px;
}

.tagline {
  color: #d4af37;
  font-weight: 600;
  letter-spacing: 4px;
  display: block;
  margin-bottom: 1rem;
}

.title {
  font-size: clamp(3rem, 8vw, 6rem);
  line-height: 1;
  font-weight: 900;
  color: white;
  margin-bottom: 1.5rem;
}

.highlight {
  color: transparent;
  -webkit-text-stroke: 1px #d4af37;
}

.description {
  font-size: 1.2rem;
  color: #bbb;
  margin-bottom: 2.5rem;
  line-height: 1.6;
}

.action-buttons {
  display: flex;
  gap: 1.5rem;
}

.btn-primary {
  background: #d4af37 !important;
  border: none !important;
  padding: 1rem 2rem !important;
  font-weight: bold !important;
  letter-spacing: 1px;
}

.btn-secondary {
  color: white !important;
  border: 1px solid rgba(255,255,255,0.3) !important;
  padding: 1rem 2rem !important;
}

.btn-secondary:hover {
  background: rgba(255,255,255,0.1) !important;
}

.bottom-gradient {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 200px;
  background: linear-gradient(transparent, #000);
}
</style>
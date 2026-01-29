<template>
  <div class="barbeiros-admin">
    <Toast />
    <ConfirmDialog />

    <div class="header-actions">
      <h2 class="title">Gerenciamento de <span class="gold">Barbeiros</span></h2>
      <Button label="Novo Barbeiro" icon="pi pi-plus" class="p-button-gold" @click="openNew" />
    </div>

    <DataTable :value="barbeiros" class="custom-table" :loading="loading" responsiveLayout="stack">
      <Column field="nome" header="Nome do Barbeiro">
        <template #body="slotProps">
          <span class="edit-link" @click="editBarbeiro(slotProps.data)">
            {{ slotProps.data.nome }}
          </span>
        </template>
      </Column>
      
      <Column header="Especialidades">
        <template #body="slotProps">
          <div class="tags-container">
            <Tag v-for="esp in slotProps.data.especialidades" :key="esp" 
                 :value="formatEnum(esp)" severity="secondary" class="custom-tag" />
          </div>
        </template>
      </Column>

      <Column header="Status">
        <template #body="slotProps">
          <Tag :value="slotProps.data.estaAtivo ? 'Ativo' : 'Inativo'" 
               :severity="slotProps.data.estaAtivo ? 'success' : 'danger'" />
        </template>
      </Column>

      <Column header="Ações">
        <template #body="slotProps">
          <Button icon="pi pi-trash" class="p-button-rounded p-button-text p-button-danger" 
                  @click="confirmDelete(slotProps.data)" />
        </template>
      </Column>
    </DataTable>

    <Dialog v-model:visible="barbeiroDialog" :header="barbeiro.id ? 'Editar Barbeiro' : 'Novo Barbeiro'" 
            :modal="true" class="p-fluid custom-dialog" :style="{ width: '50vw' }">
      
      <div class="field">
        <label for="nome">Nome</label>
        <InputText id="nome" v-model.trim="barbeiro.nome" required autofocus />
      </div>

      <div class="field">
        <label>Foto do Barbeiro</label>
        <FileUpload mode="basic" name="file" accept="image/*" :maxFileSize="1000000" 
                    @select="onFileSelect" chooseLabel="Selecionar Foto" class="p-button-outlined" />
        <small v-if="barbeiro.fotoPath" class="text-gold">Já possui foto salva.</small>
      </div>

      <div class="field">
        <label>Especialidades</label>
        <MultiSelect v-model="barbeiro.especialidades" :options="especialidadesOptions" 
                     optionLabel="label" optionValue="value" placeholder="Selecione as especialidades" />
      </div>

      <div class="field-checkbox">
        <Checkbox id="ativo" v-model="barbeiro.estaAtivo" :binary="true" />
        <label for="ativo">Barbeiro Ativo</label>
      </div>

      <div class="horarios-section">
        <h3>Horários de Trabalho</h3>
        <div v-for="(horario, index) in barbeiro.horariosTrabalho" :key="index" class="horario-row">
          <Dropdown v-model="horario.diaDaSemana" :options="diasSemanaOptions" 
                    optionLabel="label" optionValue="value" placeholder="Dia" />
          <InputMask v-model="horario.horaInicio" mask="99:99" placeholder="08:00" />
          <InputMask v-model="horario.horaFim" mask="99:99" placeholder="18:00" />
          <Button icon="pi pi-times" class="p-button-rounded p-button-danger p-button-text" @click="removeHorario(index)" />
        </div>
        <Button label="Adicionar Horário" icon="pi pi-plus" class="p-button-text p-button-sm gold-text" @click="addHorario" />
      </div>

      <template #footer>
        <Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
        <Button label="Salvar" icon="pi pi-check" class="p-button-gold" @click="saveBarbeiro" />
      </template>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useToast } from 'primevue/usetoast';
import { useConfirm } from 'primevue/useconfirm';
import barbeiroService, { Especialidade, type Barbeiro, type BarbeiroInput } from '../../services/barbeiroService';

const toast = useToast();
const confirm = useConfirm();

const barbeiros = ref<Barbeiro[]>([]);
const loading = ref(true);
const barbeiroDialog = ref(false);
const barbeiro = ref<Partial<Barbeiro>>({});
const selectedFile = ref<File | null>(null);

const especialidadesOptions = Object.values(Especialidade).map(v => ({ 
  label: v.replace(/_/g, ' '), 
  value: v 
}));

const diasSemanaOptions = [
  { label: 'Segunda-feira', value: 'MONDAY' },
  { label: 'Terça-feira', value: 'TUESDAY' },
  { label: 'Quarta-feira', value: 'WEDNESDAY' },
  { label: 'Quinta-feira', value: 'THURSDAY' },
  { label: 'Sexta-feira', value: 'FRIDAY' },
  { label: 'Sábado', value: 'SATURDAY' },
  { label: 'Domingo', value: 'SUNDAY' }
];

onMounted(() => {
  loadBarbeiros();
});

const loadBarbeiros = async () => {
  loading.value = true;
  try {
    barbeiros.value = await barbeiroService.getBarbeiros();
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao carregar lista', life: 3000 });
  } finally {
    loading.value = false;
  }
};

const onFileSelect = (event: any) => {
  selectedFile.value = event.files[0];
};

const openNew = () => {
  barbeiro.value = { nome: '', especialidades: [], horariosTrabalho: [], estaAtivo: true };
  selectedFile.value = null;
  barbeiroDialog.value = true;
};

const editBarbeiro = (data: Barbeiro) => {
  barbeiro.value = { ...data, horariosTrabalho: data.horariosTrabalho.map(h => ({...h})) };
  selectedFile.value = null;
  barbeiroDialog.value = true;
};

const addHorario = () => {
  if (!barbeiro.value.horariosTrabalho) barbeiro.value.horariosTrabalho = [];
  barbeiro.value.horariosTrabalho.push({ diaDaSemana: '', horaInicio: '', horaFim: '' });
};

const removeHorario = (index: number) => {
  barbeiro.value.horariosTrabalho?.splice(index, 1);
};

const saveBarbeiro = async () => {
  if (!barbeiro.value.nome) return;

  try {
    const input: BarbeiroInput = {
      nome: barbeiro.value.nome,
      especialidades: barbeiro.value.especialidades || [],
      horariosTrabalho: barbeiro.value.horariosTrabalho?.map(({id, ...h}) => h) || [],
      estaAtivo: barbeiro.value.estaAtivo || false,
      fotoPath: barbeiro.value.fotoPath || ''
    };

    // Se não selecionou foto nova na edição, enviamos um arquivo vazio conforme a regra do service
    const fileToSend = selectedFile.value || new File([""], "blob", { type: "image/jpeg" });

    if (barbeiro.value.id) {
      await barbeiroService.updateBarbeiro(barbeiro.value.id, input, fileToSend);
      toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Atualizado com sucesso', life: 3000 });
    } else {
      await barbeiroService.createBarbeiro(input, fileToSend);
      toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Criado com sucesso', life: 3000 });
    }

    barbeiroDialog.value = false;
    loadBarbeiros();
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao salvar informações', life: 3000 });
  }
};

const confirmDelete = (data: Barbeiro) => {
  confirm.require({
    message: `Deseja realmente excluir o barbeiro ${data.nome}?`,
    header: 'Confirmar Exclusão',
    icon: 'pi pi-exclamation-triangle',
    acceptClass: 'p-button-danger',
    acceptLabel: 'Excluir',
    rejectLabel: 'Cancelar',
    accept: async () => {
      try {
        await barbeiroService.deleteBarbeiro(data.id);
        toast.add({ severity: 'success', summary: 'Excluído', detail: 'Barbeiro removido', life: 3000 });
        loadBarbeiros();
      } catch (error) {
        toast.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir', life: 3000 });
      }
    }
  });
};

const hideDialog = () => {
  barbeiroDialog.value = false;
};

const formatEnum = (text: string) => text.replace(/_/g, ' ').toLowerCase();
</script>

<style scoped>
.title { color: white; margin: 0; }
.gold { color: #d4af37; }
.gold-text { color: #d4af37 !important; }

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

/* Fix para Fundo Escuro na Tabela */
.custom-table {
  background: #111 !important;
  border: 1px solid rgba(255,255,255,0.05);
}

:deep(.p-datatable-thead > tr > th) {
  background: #1a1a1a !important;
  color: #d4af37 !important;
  border-bottom: 1px solid #333;
}

:deep(.p-datatable-tbody > tr) {
  background: #111 !important;
  color: #ccc !important;
}

.edit-link {
  color: #d4af37;
  font-weight: bold;
  cursor: pointer;
  text-decoration: underline;
}

.tags-container { display: flex; gap: 4px; flex-wrap: wrap; }

.p-button-gold {
  background: #d4af37 !important;
  border: none !important;
  color: #000 !important;
  font-weight: bold;
}

.horario-row {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr auto;
  gap: 10px;
  margin-bottom: 10px;
  align-items: center;
}

.horarios-section {
  margin-top: 2rem;
  padding-top: 1rem;
  border-top: 1px solid #333;
}

.horarios-section h3 { color: #d4af37; font-size: 1.1rem; }

.field { margin-bottom: 1.5rem; }
.field-checkbox { display: flex; align-items: center; gap: 10px; margin-bottom: 1rem; }

:deep(.p-dialog-content) { background: #1a1a1a !important; color: white; }
:deep(.p-dialog-header) { background: #1a1a1a !important; color: #d4af37 !important; }
</style>
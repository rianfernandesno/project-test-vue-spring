<template>
  <div class="servicos-admin">
    <Toast />
    <ConfirmDialog />

    <div class="header-actions">
      <h2 class="title">
        Gerenciamento de <span class="gold">Serviços</span>
      </h2>
      <Button
        label="Novo Serviço"
        icon="pi pi-plus"
        class="p-button-gold"
        @click="openNew"
      />
    </div>

    <DataTable
      :value="servicos"
      class="custom-table"
      :loading="loading"
      responsiveLayout="stack"
    >
      <Column field="nome" header="Nome do Serviço">
        <template #body="slotProps">
          <span class="edit-link" @click="editService(slotProps.data)">
            {{ slotProps.data.nome }}
          </span>
        </template>
      </Column>

      <Column field="especialidade" header="Especialidade">
        <template #body="slotProps">
          <Tag :value="slotProps.data.especialidade" severity="secondary" class="custom-tag" />
        </template>
      </Column>

      <Column field="duracao" header="Duração">
        <template #body="slotProps">
          {{ slotProps.data.duracao }} min
        </template>
      </Column>

      <Column field="preco" header="Preço">
        <template #body="slotProps">
          <span class="gold-text">
            {{ formatCurrency(slotProps.data.preco) }}
          </span>
        </template>
      </Column>

      <Column header="Ações" style="width: 80px">
        <template #body="slotProps">
          <Button 
            icon="pi pi-trash" 
            class="p-button-rounded p-button-text p-button-danger" 
            @click="confirmDelete(slotProps.data)" 
          />
        </template>
      </Column>
    </DataTable>

    <Dialog
      v-model:visible="serviceDialog"
      :header="headerTitle"
      :modal="true"
      class="custom-modal"
      :style="{ width: '500px' }"
    >
      <div class="p-fluid">
        <div class="field mb-4">
          <label for="nome" class="label-modal">Nome do Serviço</label>
          <InputText id="nome" v-model="service.nome" placeholder="Ex: Corte Degradê" />
        </div>

        <div class="field mb-4">
          <label for="descricao" class="label-modal">Descrição</label>
          <Textarea id="descricao" v-model="service.descricao" rows="3" autoResize />
        </div>

        <div class="formgrid grid">
          <div class="field col-6">
            <label for="preco" class="label-modal">Preço (R$)</label>
            <InputNumber 
              id="preco" 
              v-model="service.preco" 
              mode="currency" 
              currency="BRL" 
              locale="pt-BR"
              class="w-full"
            />
          </div>
          <div class="field col-6">
            <label for="duracao" class="label-modal">Duração (min)</label>
            <InputNumber 
              id="duracao" 
              v-model="service.duracao" 
              suffix=" min"
              class="w-full"
            />
          </div>
        </div>

        <div class="field mt-3">
          <label for="especialidade" class="label-modal">Especialidade</label>
          <Dropdown
            id="especialidade"
            v-model="service.especialidade"
            :options="listaEspecialidades"
            placeholder="Selecione a categoria"
            class="w-full"
          />
        </div>
      </div>

      <template #footer>
        <Button label="Cancelar" icon="pi pi-times" class="p-button-text p-button-secondary" @click="hideDialog" />
        <Button label="Salvar" icon="pi pi-check" class="p-button-gold" @click="saveService" :loading="submitting" />
      </template>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useToast } from 'primevue/usetoast';
import { useConfirm } from 'primevue/useconfirm';
import servicoService, { type Servico, type ServicoInput } from '../../services/servicoService';

const toast = useToast();
const confirm = useConfirm();

const servicos = ref<Servico[]>([]);
const loading = ref(true);
const submitting = ref(false);
const serviceDialog = ref(false);
const headerTitle = ref('');

const listaEspecialidades = ref([
  'CORTE_DE_CABELO', 
  'BARBA', 
  'SOBRANCELHA', 
  'PIGMENTACAO', 
  'PROGRESSIVA', 
  'COMBO', 
  'ESTETICA'
]);

const service = ref<ServicoInput & { id?: number }>({
  nome: '',
  descricao: '',
  preco: 0,
  duracao: 0,
  especialidade: ''
});

const loadServicos = async () => {
  loading.value = true;
  try {
    const data = await servicoService.getServicos();
    servicos.value = data;
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao buscar serviços', life: 3000 });
  } finally {
    loading.value = false;
  }
};

onMounted(loadServicos);

const openNew = () => {
  service.value = { nome: '', descricao: '', preco: 0, duracao: 30, especialidade: '' };
  headerTitle.value = 'Cadastrar Novo Serviço';
  serviceDialog.value = true;
};

const editService = (data: Servico) => {
  service.value = { 
    id: data.id,
    nome: data.nome,
    descricao: data.descricao,
    preco: Number(data.preco),
    duracao: Number(data.duracao),
    especialidade: data.especialidade
  };
  headerTitle.value = 'Editar Serviço';
  serviceDialog.value = true;
};

const hideDialog = () => {
  serviceDialog.value = false;
};

const saveService = async () => {
  if (!service.value.nome || !service.value.especialidade) {
    toast.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha Nome e Especialidade', life: 3000 });
    return;
  }

  submitting.value = true;
  try {
    if (service.value.id) {
      await servicoService.updateServico(service.value.id, service.value);
      toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Serviço atualizado', life: 3000 });
    } else {
      await servicoService.createServico(service.value);
      toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Serviço criado', life: 3000 });
    }
    serviceDialog.value = false;
    loadServicos();
  } catch (error) {
    console.error(error);
    toast.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao salvar serviço', life: 3000 });
  } finally {
    submitting.value = false;
  }
};

const confirmDelete = (data: Servico) => {
  confirm.require({
    message: `Tem certeza que deseja excluir "${data.nome}"?`,
    header: 'Confirmar Exclusão',
    icon: 'pi pi-exclamation-triangle',
    acceptLabel: 'Sim, excluir',
    rejectLabel: 'Não',
    acceptClass: 'p-button-danger',
    accept: async () => {
      try {
        await servicoService.deleteServico(data.id);
        toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Serviço excluído', life: 3000 });
        loadServicos();
      } catch (error) {
        toast.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir', life: 3000 });
      }
    }
  });
};

const formatCurrency = (value: number) => {
  return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value);
};
</script>

<style scoped>
.servicos-admin { padding: 1rem; }
.gold { color: #d4af37; }
.gold-text { color: #d4af37; font-weight: bold; }

.label-modal { color: #333; font-size: 0.9rem; font-weight: 600; display: block; margin-bottom: 0.5rem; }

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.title { color: #fff; font-weight: 300; margin: 0; }

.custom-table { background: #111 !important; border-radius: 8px; overflow: hidden; }

:deep(.p-datatable-thead > tr > th) {
  background: #1a1a1a !important;
  color: #d4af37 !important;
  border-bottom: 1px solid #333;
}
:deep(.p-datatable-tbody > tr) {
  background: #111 !important;
  color: #ccc !important;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.edit-link { color: #d4af37; font-weight: bold; cursor: pointer; text-decoration: underline; }
.custom-tag { background: rgba(212, 175, 55, 0.1) !important; color: #d4af37 !important; }

.p-button-gold {
  background: #d4af37 !important;
  border: none !important;
  color: #000 !important;
  font-weight: bold;
}

:deep(.p-dialog .p-inputtext), 
:deep(.p-dialog .p-inputnumber-input),
:deep(.p-dialog .p-dropdown),
:deep(.p-dialog .p-textarea) {
  background: #ffffff !important;
  color: #333 !important; 
  border: 1px solid #ced4da !important;
  border-radius: 6px !important;
}

:deep(.p-dialog .p-inputtext:focus), 
:deep(.p-dialog .p-inputnumber-input:focus), 
:deep(.p-dialog .p-dropdown:focus) {
  border-color: #d4af37 !important;
  box-shadow: 0 0 0 2px rgba(212, 175, 55, 0.2) !important;
}

:deep(.p-dropdown .p-dropdown-label) {
  color: #333 !important;
}

.w-full { width: 100% !important; }
:deep(.p-inputnumber) { width: 100%; }

:deep(.p-dialog-header) {
  background: #f8f9fa !important;
  color: #333 !important;
  border-bottom: 1px solid #dee2e6;
}
:deep(.p-dialog-footer) {
  background: #f8f9fa !important;
  border-top: 1px solid #dee2e6;
}
</style>
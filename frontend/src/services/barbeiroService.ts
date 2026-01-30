import api from "./api";

export enum Especialidade {
  CORTE_DE_CABELO = "CORTE_DE_CABELO",
  BARBA = "BARBA",
  SOBRANCELHA = "SOBRANCELHA",
  PIGMENTACAO = "PIGMENTACAO",
  QUIMICA = "QUIMICA",
}

export interface HorarioTrabalho {
  id?: number; // Opcional para criação
  diaDaSemana: string;
  horaInicio: string;
  horaFim: string;
}

// Representa o BarbeiroOutput vindo do Back-end
export interface Barbeiro {
  id: number;
  nome: string;
  fotoPath: string;
  especialidades: Especialidade[];
  horariosTrabalho: HorarioTrabalho[];
  estaAtivo: boolean;
}

// Representa o BarbeiroInput para envio
export interface BarbeiroInput {
  nome: string;
  fotoPath?: string;
  especialidades: Especialidade[];
  horariosTrabalho: Omit<HorarioTrabalho, "id">[];
  estaAtivo: boolean;
}

const barbeiroService = {
  // GET - Listar todos
  async getBarbeiros(): Promise<Barbeiro[]> {
    const { data } = await api.get<Barbeiro[]>("/barbeiro");
    return data;
  }, // Vírgula aqui

  // POST - Criar novo barbeiro
async createBarbeiro(input: BarbeiroInput, file?: File): Promise<Barbeiro> {
    const formData = new FormData();
    const json = JSON.stringify(input);
    const blob = new Blob([json], { type: "application/json" });

    formData.append("input", blob);
    if (file && file.size > 0) formData.append("file", file);

    // O Axios detecta o FormData e define o Header correto sozinho
    const { data } = await api.post<Barbeiro>("/barbeiro", formData);
    return data;
  },

  // PUT - Atualizar barbeiro existente
 async updateBarbeiro(id: number, input: BarbeiroInput, file?: File): Promise<Barbeiro> {
    const formData = new FormData();
    const json = JSON.stringify(input);
    const blob = new Blob([json], { type: "application/json" });

    formData.append("input", blob);
    if (file && file.size > 0) formData.append("file", file);

    // REMOVIDO: headers: { "Content-Type": "multipart/form-data" }
    const { data } = await api.put<Barbeiro>(`/barbeiro/${id}`, formData);
    return data;
  },

  // DELETE - Remover barbeiro
  async deleteBarbeiro(id: number): Promise<void> {
    await api.delete(`/barbeiro/${id}`);
  },
};

export default barbeiroService;

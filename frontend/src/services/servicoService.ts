import api from './api'; 

export interface Servico {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  duracao: number;
  especialidade: string;
}

// Interface para os dados de entrada (Input do Java)
export interface ServicoInput {
  nome: string;
  descricao: string;
  preco: number;
  duracao: number;
  especialidade: string;
}

const servicoService = {
  // GET /servicos
  async getServicos(): Promise<Servico[]> {
    const response = await api.get<Servico[]>('/servicos');
    return response.data;
  },

  // POST /servicos
  async createServico(servico: ServicoInput): Promise<Servico> {
    const response = await api.post<Servico>('/servicos', servico);
    return response.data;
  },

  // PUT /servicos/{id}
  async updateServico(id: number, servico: ServicoInput): Promise<Servico> {
    const response = await api.put<Servico>(`/servicos/${id}`, servico);
    return response.data;
  },

  // DELETE /servicos/{id}
  async deleteServico(id: number): Promise<void> {
    await api.delete(`/servicos/${id}`);
  }
};

export default servicoService;
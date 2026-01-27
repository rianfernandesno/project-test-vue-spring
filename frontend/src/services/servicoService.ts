import api from './api'; 

export interface Servico {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  duracao: number;
  especialidade: string;
}

const servicoService = {
  async getServicos(): Promise<Servico[]> {
    const response = await api.get<Servico[]>('/servicos');
    return response.data;
  }
};

export default servicoService;
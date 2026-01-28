import api from './api';

export enum Especialidade {
  CORTE_DE_CABELO = "CORTE_DE_CABELO",
  BARBA = "BARBA",
  SOBRANCELHA = "SOBRANCELHA",
  PIGMENTACAO = "PIGMENTACAO",
  QUIMICA = "QUIMICA"
}

export interface HorarioTrabalho {
  id: number;
  diaDaSemana: string; 
  horaInicio: string;  
  horaFim: string;
}

export interface Barbeiro {
  id: number;
  nome: string;
  fotoPath: string;
  especialidades: Especialidade[];
  horariosTrabalho: HorarioTrabalho[]; 
  estaAtivo: boolean;
}

const barbeiroService = {
  async getBarbeiros(): Promise<Barbeiro[]> {
    const { data } = await api.get<Barbeiro[]>('/barbeiro');
    return data;
  },
};

export default barbeiroService;
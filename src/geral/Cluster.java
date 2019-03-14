package geral;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Cluster com lista de processos e controle de eleição.
 * 
 * @author Vinícius R. Biavatti
 */
public class Cluster {

    /**
     * Lista de Processos Volátil (Thread Safe)
     */
    private volatile List<Processo> listaProcessos;
    
    /**
     * Variável de controle de eleições (Thread Safe)
     */
    private volatile boolean eleicaoAtiva = false;
    
    /**
     * Variável com a referência para o Coordenador (Thread Safe)
     */
    private volatile Processo coordenador = null;
    
    /**
     * Construir Cluster
     */
    public Cluster() {
        listaProcessos = new CopyOnWriteArrayList<>();
    }
    
    /**
     * Obter quantidade de registros vivos na lista de processos
     * @return 
     */
    public int getQtdProcessosVivos() {
        int contagem = 0;
        for(Processo p : getListaProcessos()) {
            if(p.isVivo()) {
                contagem++;
            }
        }
        return contagem;
    }

    /**
     * Matar processo coordenador
     */
    public void matarCoordenador() {
        coordenador.setVivo(false);
    }
    
    /**
     * Adicionar processo
     * @param p 
     */
    public void addProcesso(Processo p) {
        getListaProcessos().add(p);
    }
    
    /**
     * Obter situações dos processos em uma String
     * @return 
     */
    public String toStringProcessos() {
        String resultado = "";
        for(Processo p : getListaProcessos()) {
            if(p.isVivo()) {
                resultado += p + "\n";
            }
        }
        return resultado;
    }

    /*
     * Get e Set
     */
    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

    public Boolean isEleicaoAtiva() {
        return eleicaoAtiva;
    }

    public void setEleicaoAtiva(Boolean eleicaoAtiva) {
        this.eleicaoAtiva = eleicaoAtiva;
    }

    public Processo getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Processo coordenador) {
        this.coordenador = coordenador;
    }
}

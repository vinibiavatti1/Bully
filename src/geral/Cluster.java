package geral;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
     * Formatador de Data para informação de ações
     */
    private SimpleDateFormat formatter;
    
    /**
     * Variável com a referência para o Coordenador (Thread Safe)
     */
    private volatile Processo coordenador = null;
    
    /**
     * Construir Cluster
     */
    public Cluster() {
        listaProcessos = new CopyOnWriteArrayList<>();
        formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
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
        System.out.println(formatter.format(new Date()) + " - Matou o coordenador");
        System.out.println("---");
        System.out.println();
    }

    public void getCoordenadorVivo() {
        boolean notifyed = false;
        if (getListaProcessos().size() < 2) {
            notifyed = true;
        }
        do {
            Processo p = getListaProcessos().get(new Random().nextInt(getListaProcessos().size()));
            if(!p.isCoordenador()) {
                if (!p.verificarCoordenadorVivo()) {
                    if(!isEleicaoAtiva()) {
                        System.out.println(formatter.format(new Date()) + " - Iniciou um processo de eleição.");
                        setEleicaoAtiva(true);
                        p.eleicao();
                    }
                }
                notifyed = true;
            }
        } while (!notifyed);
    }
    
    /**
     * Adicionar processo
     * @param p 
     */
    public void addProcesso(Processo p) {
        getListaProcessos().add(p);
    }
    
    /**
     * Matar processo aleatório
     * @param incluirCoordenador 
     */
    public void matarProcessoAleatorio(boolean incluirCoordenador) {
        boolean matou = false;
        do {
            Processo p = getListaProcessos().get(new Random().nextInt(getListaProcessos().size()));
            if(p.isCoordenador()) {
                if(incluirCoordenador) {
                    p.setVivo(false);
                    matou = true;
                }
            } else {
                p.setVivo(false);
                matou = true;
            }
        } while (!matou);

        System.out.println(formatter.format(new Date()) + " - Matou um processo aleatório");
        System.out.println(toStringProcessos());
        System.out.println("---");
        System.out.println();
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

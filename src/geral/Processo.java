package geral;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Processo de execução assíncrona com funções implementadas para
 * tratar o algoritmo Bully.
 * 
 * @author VINICIUS
 */
public class Processo implements Runnable {

    private boolean vivo = true;
    private Processo coordenador = null; 
    private List<Processo> listaProcessos = null;
    private int id;

    /**
     * Construir Processo
     * @param listaProcessos 
     */
    public Processo(List<Processo> listaProcessos) {
        this.setListaProcessos(listaProcessos);
        gerarId();
    }
    
    /**
     * Construir Processo com Coordenador
     * @param listaProcessos
     * @param coordenador 
     */
    public Processo(List<Processo> listaProcessos, Processo coordenador) {
        this.setListaProcessos(listaProcessos);
        this.setCoordenador(coordenador);
        gerarId();
    }
    
    /**
     * Iniciar execução de Thread
     */
    public void iniciarExecucao() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(vivo) {
            Util.delay(Config.DELAY_EXECUCAO);
            if(!verificarCoordenadorVivo()) {
                eleicao();
            }
        }
    }
    
    /**
     * Eleição para decidir novo coordenador
     */
    public synchronized void eleicao() {
        List<Processo> processosVivos = new ArrayList<>();
        for(Processo p : getListaProcessos()) {
            if(p.getId() > getId()) {
                boolean pong = ping(p);
                if(pong) {
                    processosVivos.add(p);
                }
            }
        }
        if(processosVivos.isEmpty()) {
            if(isVivo()) {
                setCoordenador(this);
                atualizarCoordenadorProcessos(this);
            }
            return;
        }
        processosVivos.stream().forEach(Processo::eleicao);
    }
    
    /**
     * Atualizar coordenador para os processos
     * @param novoCoordenador 
     */
    public synchronized void atualizarCoordenadorProcessos(Processo novoCoordenador) {
        for(Processo p : getListaProcessos()) {
            if(p == this) continue;
            p.setCoordenador(novoCoordenador);
        }
    }
    
    /**
     * Executar teste de ping ao processo
     * @param p
     * @return 
     */
    public boolean ping(Processo p) {
        return p.pong();
    }
    
    /**
     * Responder teste de ping
     * @return 
     */
    public boolean pong() {
        return isVivo();
    }
    
    /**
     * Gerar ID para o processo
     */
    public void gerarId() {
        boolean idValido = false;
        int id = 0;
        do {
            idValido = true;
            Random random = new Random();
            id = random.nextInt(Config.ID_MAX_PROCESSO);
            for(Processo p : getListaProcessos()) {
                if(p.getId() == id) {
                    idValido = false;
                }
            }
        } while(!idValido);
        setId(id);
    }
    
    /**
     * Retornar lista de processos em String
     * @return 
     */
    public String toStringListaProcessos() {
        String resultado = "[";
        String virgula = "";
        for(Processo p : getListaProcessos()) {
            resultado += virgula + p.getId();
            virgula = ", ";
        }
        return resultado + "]";
    }
    
    /**
     * Verificar se coordenador está vivo
     * @return 
     */
    public boolean verificarCoordenadorVivo() {
        return getCoordenador().isVivo();
    }

    /*
     * Get e Set
     */
    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public Processo getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Processo coordenador) {
        this.coordenador = coordenador;
    }

    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public void setListaProcessos(List<Processo> listaProcessos) {
        this.listaProcessos = listaProcessos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public boolean isCoordenador() {
        return getCoordenador() == this;
    }

    @Override
    public String toString() {
        return "Processo " + getId() + (isCoordenador() ? "\t(C)" : "\t( )");
    }
    
    
}

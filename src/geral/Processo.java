package geral;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Processo de execução assíncrona com funções implementadas para
 * tratar o algoritmo Bully.
 * 
 * @author Vinícius R. Biavatti
 */
public class Processo implements Runnable {

    /**
     * Definir se processo está em execução ou não
     */
    private boolean vivo = true;

    /**
     * Formatador de Data para informação de ações
     */
    private SimpleDateFormat formatter;
    
    /**
     * PID - Identificador do processo
     */
    private int id;
    
    /**
     * Referência ao Cluster
     */
    private Cluster cluster = null;

    /**
     * Construir Processo
     * @param cluster 
     */
    public Processo(Cluster cluster) {
        setCluster(cluster);
        cluster.addProcesso(this);
        gerarId();
        formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
    }
    
    /**
     * Construir processo com controle de execução no construtor
     * @param cluster
     * @param iniciarExecucao 
     */
    public Processo(Cluster cluster, boolean iniciarExecucao){
        this(cluster);
//        if(iniciarExecucao) {
//            this.iniciarExecucao();
//        }
    }
    
    /**
     * Iniciar execução de Thread<br>
     * <code>new Thread(this).start();</code>
     */
    public void iniciarExecucao() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(vivo) {
            Util.delay(Config.DELAY_EXECUCAO);
            if(!verificarCoordenadorVivo()) {
                if(!getCluster().isEleicaoAtiva()) {
                    System.out.println(formatter.format(new Date()) + " - Iniciou um processo de eleição.");
                    getCluster().setEleicaoAtiva(true);
                    eleicao();
                }
            }
        }
    }
    
    /**
     * Eleição para decidir novo coordenador
     */
    public synchronized void eleicao() {
        List<Processo> processosVivos = new ArrayList<>();
        for(Processo p : getCluster().getListaProcessos()) {
            if(p.getId() > getId()) {
                boolean pong = ping(p);
                if(pong) {
                    processosVivos.add(p);
                }
            }
        }
        if(processosVivos.isEmpty()) {
            getCluster().setCoordenador(this);
            atualizarCoordenador(this);
            getCluster().setEleicaoAtiva(false);
            return;
        }
        processosVivos.stream().forEach(Processo::eleicao);
    }
    
    /**
     * Atualizar coordenador no Cluster
     * @param novoCoordenador 
     */
    public synchronized void atualizarCoordenador(Processo novoCoordenador) {
        System.out.println(formatter.format(new Date()) + " - Novo coordenador com o PID:" + novoCoordenador.getId());
        getCluster().setCoordenador(novoCoordenador);
    }
    
    /**
     * Executar teste de ping ao processo.
     * @param processo
     * @return 
     */
    public boolean ping(Processo processo) {
        return processo.pong();
    }
    
    /**
     * Responder teste de ping.
     * @return 
     */
    public boolean pong() {
        return isVivo();
    }
    
    /**
     * Gerar ID para o processo. O ID é gerado aleatoriamente e não será igual
     * a um ID existente.
     */
    public void gerarId() {
        boolean idValido = false;
        int id = 0;
        do {
            idValido = true;
            Random random = new Random();
            id = random.nextInt(Config.ID_MAX_PROCESSO);
            for(Processo p : getCluster().getListaProcessos()) {
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
        for(Processo p : getCluster().getListaProcessos()) {
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
        System.out.println(formatter.format(new Date()) + " - Processo verifica se coordenador está vivo.");
        return getCluster().getCoordenador().isVivo();
    }
    
    @Override
    public String toString() {
        return "Processo " + getId() + (isCoordenador() ? "\t(C)" : "\t( )");
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public boolean isCoordenador() {
        return getCluster().getCoordenador() == this;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}

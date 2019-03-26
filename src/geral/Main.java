package geral;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>FURB Universidade Regional de Blumenau</b><br>
 * Sistemas Distribuidos - Trabalho 1<br>
 * Professor: Aurélio Faustino Hoppe<br>
 * Alunos:
 * <ul>
 *   <li>Vinícius Reif Biavatti</li>
 *   <li>Bryan Leite</li>
 *   <li>Alexandre Thurow</li>
 * </ul>
 * 
 * Classe de criação de eventos sob processos no Cluster.
 * 
 * @author Vinícius R. Biavatti
 */
public class Main {

    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

        System.out.println("Sistemas Distribuidos - Trabalho 1");
        System.out.println("Alunos: Vinicius / Bryan / Alexandre");
        System.out.println("Legenda: (C) - Coordenador");
        System.out.println();

        // Criar Cluster
        Cluster cluster = new Cluster();

        // Criar processos iniciais de exemplo
        Processo p1 = new Processo(cluster, true);
        cluster.setCoordenador(p1);
        System.out.println(formatter.format(new Date()) + " - Criou processo com PID: " + p1.getId());
        System.out.println(cluster.toStringProcessos());
        System.out.println("---");
        System.out.println();
        /*
         * Thread para criar novos processos
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    Util.delay(Config.DELAY_CRIAR_PROCESSO);
                    Processo p = new Processo(cluster, true);
                    System.out.println(formatter.format(new Date()) + " - Criou processo com pid " + p.getId());
                    System.out.println(cluster.toStringProcessos());
                    System.out.println("---");
                    System.out.println();
                }
            }
        }).start();

        /*
         * Thread para verificar se o coordenador está vivo
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    Util.delay(Config.DELAY_EXECUCAO);
                    cluster.getCoordenadorVivo();
                }
            }
        }).start();
        
        /*
         * Thread para matar coordenador
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    Util.delay(Config.DELAY_MATAR_COORDENADOR);
                    cluster.matarCoordenador();
                }
            }
        }).start();
        
        /*
         * Thread para matar um processo aleatório
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    Util.delay(Config.DELAY_MATAR_PROCESSO);
                    cluster.matarProcessoAleatorio(false);
                }
            }
        }).start();
    }

}

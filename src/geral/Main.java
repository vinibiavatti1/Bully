package geral;

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

        System.out.println("Sistemas Distribuidos - Trabalho 1");
        System.out.println("Alunos: Vinicius / Bryan / Alexandre");
        System.out.println("Legenda: (C) - Coordenador");
        System.out.println();

        // Criar Cluster
        Cluster cluster = new Cluster();

        // Criar processos iniciais de exemplo
        Processo p1 = new Processo(cluster, true);
        Processo p2 = new Processo(cluster, true);
        Processo p3 = new Processo(cluster, true);
        Processo p4 = new Processo(cluster, true);
        Processo p5 = new Processo(cluster, true);
        cluster.setCoordenador(p1);

        /*
         * Thread para criar novos processos
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    Util.delay(Config.DELAY_CRIAR_PROCESSO);
                    Processo p = new Processo(cluster, true);
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

                }
            }
        }).start();
        
        /*
         * Thread para imprimir situação dos processos
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    Util.delay(Config.DELAY_IMPRIMIR_PROCESSOS);
                    System.out.println(cluster.toStringProcessos());
                    System.out.println("---");
                }
            }
        }).start();
        
        /*
         * Loop Principal
         */
        for(;;) {}
    }

}

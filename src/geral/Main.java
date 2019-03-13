package geral;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * FURB Universidade Regional de Blumenau<br>
 * Sistemas Distribuidos<br>
 * Trabalho 1<br>
 * Alunos: Vinícius / Bryan / ???
 * 
 * @author VINICIUS
 */
public class Main {
    
    
    
    /**
     * Main
     * @param args 
     */
    public static void main(String[] args) {
        
        System.out.println("Sistemas Distribuidos - Trabalho 1");
        System.out.println("Alunos: Vinicius / Bryan / ???");
        System.out.println("Legenda: (C) - Coordenador");
        System.out.println();
        
        List<Processo> listaProcessos = new ArrayList<>();
        
        Processo p1 = new Processo(listaProcessos);
        Processo p2 = new Processo(listaProcessos, p1);
        Processo p3 = new Processo(listaProcessos, p1);
        Processo p4 = new Processo(listaProcessos, p1);
        Processo p5 = new Processo(listaProcessos, p1);
        p1.setCoordenador(p1);
        
        listaProcessos.add(p1);
        listaProcessos.add(p2);
        listaProcessos.add(p3);
        
        p1.iniciarExecucao();
        p2.iniciarExecucao();
        p3.iniciarExecucao();
        
        int contador = 0;
        for(;;) {
            Util.delay(Config.DELAY_CRIAR_PROCESSO);
            listaProcessos.stream()
                    .filter(p -> p.isVivo())
                    .forEach(System.out::println);
            System.out.println("---");
           
            if(new Random().nextBoolean() || listaProcessos.size() <= 1) {
                Processo coordenador = Main.getCoordenador(listaProcessos);
                Processo p = new Processo(listaProcessos, coordenador);
                listaProcessos.add(p);
                p.iniciarExecucao();
            } else {
                Main.matarCoordenador(listaProcessos);
            }
              
        }
    }
    
    /**
     * Obter coordenador
     * @param listaProcessos
     * @return 
     */
    public static Processo getCoordenador(List<Processo> listaProcessos) {
        for(Processo p : listaProcessos) {
            if(p.isCoordenador()) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Matar processo coordenador
     * @param listaProcessos 
     */
    public static void matarCoordenador(List<Processo> listaProcessos) {
        for(Processo p : listaProcessos) {
            if(p.isCoordenador()) {
                p.setVivo(false);
                return;
            }
        }
    }
    
}

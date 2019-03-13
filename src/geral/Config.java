package geral;

/**
 * Configurações gerais da aplicação.
 * @author VINICIUS
 */
public class Config {
    
    /**
     * Delay para a criação de cda processo.
     */
    public static final int DELAY_CRIAR_PROCESSO = 1000;
    
    /**
     * Delay para matar processo.
     */
    public static final int DELAY_MATAR_PROCESSO = 1000;

    /**
     * Delay para envio de mensagem.
     */
    public static final int DELAY_ENVIAR_MSG = 0;
    
    /**
     * Delay para resposta de mensagem.
     */
    public static final int DELAY_RECEBER_MSG = 0;
    
    /**
     * Delay de execução de processo.
     */
    public static final int DELAY_EXECUCAO = 1000;
    
    /**
     * Quantidade de IDs disponíveis para geração de PID de processo.
     */
    public static final int ID_MAX_PROCESSO = 9999;
    
}

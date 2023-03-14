package cobrinha.jogo;

public class WrongNameFormatException extends Exception {
    public WrongNameFormatException() {
        super("O nome inserido está em formato inválido!");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package br.ufjf.dcc.dcc025.jogodavelha;

/*
 * @author lucas
 */
import java.util.Scanner;
import java.util.Random;

//TODO: Talvez exportar toda a logica de jogada e simbolo para uma classe Jogador

public class JogoDaVelha {

    private Tabuleiro tabuleiro;
    private Scanner scanner;
    private char jogadorAtual;
    private char jogadorHumano;
    private char jogadorComputador;
    private boolean modoSolo;
    private Random random;

    // Codigos de cor ANSI para imprimir bonitinho
    private static String ANSI_RESET = "\u001B[0m";
    private static String ANSI_RED = "\u001B[31m";
    private static String ANSI_GREEN = "\u001B[32m";
    private static String ANSI_BLUE = "\u001B[34m";
    private static String ANSI_PURPLE = "\u001B[35m";

    private String banner = "       __    ______     _______   ______      _______       ___        ____    ____  _______  __       __    __       ___      \n"
            + "      |  |  /  __  \\   /  _____| /  __  \\    |       \\     /   \\       \\   \\  /   / |   ____||  |     |  |  |  |     /   \\     \n"
            + "      |  | |  |  |  | |  |  __  |  |  |  |   |  .--.  |   /  ^  \\       \\   \\/   /  |  |__   |  |     |  |__|  |    /  ^  \\    \n"
            + ".--.  |  | |  |  |  | |  | |_ | |  |  |  |   |  |  |  |  /  /_\\  \\       \\      /   |   __|  |  |     |   __   |   /  /_\\  \\   \n"
            + "|  `--'  | |  `--'  | |  |__| | |  `--'  |   |  '--'  | /  _____  \\       \\    /    |  |____ |  `----.|  |  |  |  /  _____  \\  \n"
            + " \\______/   \\______/   \\______|  \\______/    |_______/ /__/     \\__\\       \\__/     |_______||_______||__|  |__| /__/     \\__\\ \n"
            + "                                                                                                                               ";

    public JogoDaVelha() {
        tabuleiro = new Tabuleiro();
        scanner = new Scanner(System.in);
        random = new Random();
    }

    public void iniciar() {
        escolherModo();
        escolherSimbolo();
        jogar();
    }

    private void escolherModo() {
        System.out.println(ANSI_RED + banner + ANSI_RESET);
        System.out.println("Escolha o modo de jogo:");
        System.out.println(ANSI_BLUE + "1 - Jogador vs Bot" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "2 - Jogador vs Jogador" + ANSI_RESET);
        int escolha = scanner.nextInt();
        scanner.nextLine();
        modoSolo = (escolha == 1);
    }

    private void escolherSimbolo() {
        System.out.println(ANSI_PURPLE + "Escolha seu símbolo (X ou O):" + ANSI_RESET);
        jogadorHumano = scanner.nextLine().toUpperCase().charAt(0);
        if (jogadorHumano != 'X' && jogadorHumano != 'O') {
            jogadorHumano = 'X';
        }
        if (jogadorHumano == 'X') {
            jogadorComputador = 'O';
        } else {
            jogadorComputador = 'X';
        }
        jogadorAtual = jogadorHumano;
    }

    private void jogar() {
        boolean jogoAtivo = true;
        while (jogoAtivo) {
            tabuleiro.imprimirTabuleiro();
            if (modoSolo && jogadorAtual == jogadorComputador) {
                jogadaComputador();
            } else {
                System.out.println(ANSI_GREEN + "Jogador " + jogadorAtual + ", escolha sua jogada no formato (x,y): " + ANSI_RESET);
                String entrada = scanner.nextLine();
                int[] posicao = obterPosicao(entrada);

                if (posicao != null && tabuleiro.realizarJogada(posicao[0], posicao[1], jogadorAtual)) {
                    if (verificarVencedor()) {
                        tabuleiro.imprimirTabuleiro();
                        System.out.println(ANSI_GREEN + "Jogador " + jogadorAtual + " venceu!" + ANSI_RESET);
                        jogoAtivo = false;
                    } else if (tabuleiro.getHistorico().size() == 9) {
                        tabuleiro.imprimirTabuleiro();
                        System.out.println(ANSI_BLUE + "Empate!" + ANSI_RESET);
                        jogoAtivo = false;
                    } else {
                        trocarJogador();
                    }
                } else {
                    System.out.println("Jogada inválida! Tente novamente.");
                }
            }
        }
        tabuleiro.printHistorico();
    }

    private void jogadaComputador() {
        int linha, coluna;
        do {
            linha = random.nextInt(3) + 1;
            coluna = random.nextInt(3) + 1;
        } while (!tabuleiro.realizarJogada(linha, coluna, jogadorComputador));

        System.out.println(ANSI_RED + "Computador jogou em (" + linha + "," + coluna + ")" + ANSI_RESET);

        if (verificarVencedor()) {
            tabuleiro.imprimirTabuleiro();
            System.out.println(ANSI_GREEN + "Jogador " + jogadorAtual + " venceu!" + ANSI_RESET);
        } else if (tabuleiro.getHistorico().size() == 9) {
            tabuleiro.imprimirTabuleiro();
            System.out.println(ANSI_BLUE + "Empate!" + ANSI_RESET);
        } else {
            trocarJogador();
        }
    }

    private int[] obterPosicao(String entrada) {
        if (entrada.matches("\\(\\d,\\d\\)")) {
            int linha = Character.getNumericValue(entrada.charAt(1));
            int coluna = Character.getNumericValue(entrada.charAt(3));
            if (linha >= 1 && linha <= 3 && coluna >= 1 && coluna <= 3) {
                return new int[]{linha, coluna};
            }
        }
        return null;
    }

    private void trocarJogador() {
        if (jogadorAtual == jogadorHumano) {
            jogadorAtual = jogadorComputador;
        } else {
            jogadorAtual = jogadorHumano;
        }
    }

    //TODO: Mudar verificacao. Essa eh bem gambiarra
    private boolean verificarVencedor() {
        String[][] combinacoesVitoria = {
            {"1,1", "1,2", "1,3"},
            {"2,1", "2,2", "2,3"},
            {"3,1", "3,2", "3,3"},
            {"1,1", "2,1", "3,1"},
            {"1,2", "2,2", "3,2"},
            {"1,3", "2,3", "3,3"},
            {"1,1", "2,2", "3,3"},
            {"1,3", "2,2", "3,1"}
        };

        for (String[] combinacao : combinacoesVitoria) {
            if (tabuleiro.get(combinacao[0]) == jogadorAtual
                    && tabuleiro.get(combinacao[1]) == jogadorAtual
                    && tabuleiro.get(combinacao[2]) == jogadorAtual) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        JogoDaVelha jogo = new JogoDaVelha();
        jogo.iniciar();
    }
}
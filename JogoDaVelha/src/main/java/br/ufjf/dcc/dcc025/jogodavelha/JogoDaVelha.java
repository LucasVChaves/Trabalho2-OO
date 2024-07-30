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
        System.out.println(banner);
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Jogador vs Bot");
        System.out.println("2 - Jogador vs Jogador");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        modoSolo = (escolha == 1);
    }

    private void escolherSimbolo() {
        System.out.println("Escolha seu símbolo (X ou O):");
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
                System.out.println("Jogador " + jogadorAtual + ", escolha sua jogada no formato (x,y): ");
                String entrada = scanner.nextLine();
                int[] posicao = obterPosicao(entrada);

                if (posicao != null && tabuleiro.realizarJogada(posicao[0], posicao[1], jogadorAtual)) {
                    if (verificarVencedor()) {
                        tabuleiro.imprimirTabuleiro();
                        System.out.println("Jogador " + jogadorAtual + " venceu!");
                        jogoAtivo = false;
                    } else if (tabuleiro.getHistorico().size() == 9) {
                        tabuleiro.imprimirTabuleiro();
                        System.out.println("Empate!");
                        jogoAtivo = false;
                    } else {
                        trocarJogador();
                    }
                } else {
                    System.out.println("Jogada inválida! Tente novamente.");
                }
            }
        }
        System.out.println("Histórico de jogadas: " + tabuleiro.getHistorico());
    }

    private void jogadaComputador() {
        int linha, coluna;
        do {
            linha = random.nextInt(3) + 1;
            coluna = random.nextInt(3) + 1;
        } while (!tabuleiro.realizarJogada(linha, coluna, jogadorComputador));

        System.out.println("Computador jogou em (" + linha + "," + coluna + ")");

        if (verificarVencedor()) {
            tabuleiro.imprimirTabuleiro();
            System.out.println("Jogador " + jogadorAtual + " venceu!");
        } else if (tabuleiro.getHistorico().size() == 9) {
            tabuleiro.imprimirTabuleiro();
            System.out.println("Empate!");
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

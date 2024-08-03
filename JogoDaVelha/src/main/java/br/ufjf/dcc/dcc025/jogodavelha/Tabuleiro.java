/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufjf.dcc.dcc025.jogodavelha;

/*
 *
 * @author lucas
 */
import java.util.HashMap;
import java.util.ArrayList;

public class Tabuleiro {
    private HashMap<String, Character> tabuleiro;

    private ArrayList<String> historico;

    public Tabuleiro() {
        tabuleiro = new HashMap<>();
        historico = new ArrayList<>();
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                tabuleiro.put(i + "," + j, ' ');
            }
        }
    }

    public boolean realizarJogada(int linha, int coluna, char jogador) {
        String posicao = linha + "," + coluna;
        if (tabuleiro.get(posicao) == ' ') {
            tabuleiro.put(posicao, jogador);
            historico.add(jogador + ": (" + linha + "," + coluna + ")");
            return true;
        }
        return false;
    }

    public void imprimirTabuleiro() {
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print(" " + tabuleiro.get(i + "," + j));
                if (j < 3) System.out.print(" |");
            }
            System.out.println();
            if (i < 3) System.out.println("---+---+---");
        }
    }

    public ArrayList<String> getHistorico() {
        return historico;
    }

    public void printHistorico() {
        System.out.println("Histórico de Jogadas:");
        int rodadas = 1;
        for (String jogada: historico) {
            System.out.println("Rodada " + rodadas + " - " + jogada);
            rodadas++;
        }
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    public char get(String posicao) {
        return tabuleiro.getOrDefault(posicao, ' ');
    }
}

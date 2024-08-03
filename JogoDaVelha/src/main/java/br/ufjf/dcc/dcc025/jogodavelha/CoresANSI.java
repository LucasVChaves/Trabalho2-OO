package br.ufjf.dcc.dcc025.jogodavelha;

public enum CoresANSI {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    PURPLE("\u001B[35m"),
    BLUE("\u001B[34m"),
    GREEN("\u001B[32m");

    private String codigo;

    CoresANSI(String codigo) {
        this.codigo = codigo;
    }

    public String getCode() {
        return codigo;
    }
}

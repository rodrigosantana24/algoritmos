import java.util.Scanner;

public class P02 {

    static class Minerador {
        int chave;
        int capacidade;
        int coletado;
        int tempoViagem;
        int estado; 
        boolean recemAlocado;

        Minerador(int chave, int capacidade) {
            this.chave = chave;
            this.capacidade = capacidade;
            this.coletado = 0;
            this.tempoViagem = 0;
            this.estado = 0;
            this.recemAlocado = false;
        }
    }

    static class Fila {
        Minerador[] elementos;
        int inicio;
        int fim;
        int qtd;

        Fila(int capacidade) {
            this.elementos = new Minerador[capacidade];
            this.inicio = 0;
            this.fim = 0;
            this.qtd = 0;
        }

        void enfileirar(Minerador m) {
            elementos[fim] = m;
            fim = (fim + 1) % elementos.length;
            qtd = qtd + 1;
        }

        Minerador desenfileirar() {
            if (qtd == 0) {
                return null;
            }
            Minerador m = elementos[inicio];
            inicio = (inicio + 1) % elementos.length;
            qtd = qtd - 1;
            return m;
        }

        boolean vazia() {
            return qtd == 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextInt()) {
            int t = scanner.nextInt();
            int n = scanner.nextInt();
            int d = scanner.nextInt();
            int r = scanner.nextInt();

            Minerador[] mineradores = new Minerador[n];
            int i = 0;
            while (i < n) {
                int chave = scanner.nextInt();
                int cap = scanner.nextInt();
                mineradores[i] = new Minerador(chave, cap);
                i = i + 1;
            }

            System.out.println("novo jogo comecou.");
            System.out.println("etapa de inicializacao.");

            Minerador[] posicoes = new Minerador[t];
            Fila[] filasEspera = new Fila[t];
            
            int k = 0;
            while (k < t) {
                filasEspera[k] = new Fila(n);
                k = k + 1;
            }

            int qtdOcupada = 0;
            int idx = 0;

            while (idx < n) {
                Minerador m = mineradores[idx];
                int h = m.chave % t;
                if (h < 0) {
                    h = h + t;
                }

                if (qtdOcupada == t) {
                    System.out.println("minerador " + m.chave + " inserido na fila de espera da mina " + h + ".");
                    filasEspera[h].enfileirar(m);
                    m.estado = 3;
                } else if (posicoes[h] == null) {
                    System.out.println("minerador " + m.chave + " inserido na mina " + h + ".");
                    posicoes[h] = m;
                    qtdOcupada = qtdOcupada + 1;
                    m.estado = 0;
                } else {
                    System.out.println("minerador " + m.chave + " colidiu com minerador " + posicoes[h].chave + " na mina " + h + ".");
                    int pos = (h + 1) % t;
                    while (posicoes[pos] != null) {
                        pos = (pos + 1) % t;
                    }
                    System.out.println("minerador " + m.chave + " inserido na mina " + pos + ".");
                    posicoes[pos] = m;
                    qtdOcupada = qtdOcupada + 1;
                    m.estado = 0;
                }
                idx = idx + 1;
            }

            int j = 0;
            while (j < t) {
                if (posicoes[j] == null) {
                    System.out.print("vazia ");
                } else {
                    System.out.print(posicoes[j].chave + "(" + posicoes[j].coletado + ") ");
                }
                j = j + 1;
            }
            System.out.println();

            Fila filaViajantes = new Fila(n);
            int totalBase = 0;
            int turno = 1;

            while (turno <= r) {
                System.out.println("turno " + turno + " comecou.");

                int p = 0;
                while (p < t) {
                    if (posicoes[p] != null) {
                        if (posicoes[p].recemAlocado) {
                            posicoes[p].recemAlocado = false;
                        } else {
                            posicoes[p].coletado = posicoes[p].coletado + 1;
                        }
                    }
                    p = p + 1;
                }

                p = 0;
                while (p < t) {
                    if (posicoes[p] == null) {
                        System.out.print("vazia ");
                    } else {
                        System.out.print(posicoes[p].chave + "(" + posicoes[p].coletado + ") ");
                    }
                    p = p + 1;
                }
                System.out.println();

                Fila viajantesNovos = new Fila(n);
                p = 0;
                while (p < t) {
                    if (posicoes[p] != null && posicoes[p].coletado == posicoes[p].capacidade) {
                        Minerador m = posicoes[p];
                        System.out.println("minerador " + m.chave + " a caminho da base.");
                        m.estado = 1;
                        m.tempoViagem = d;
                        viajantesNovos.enfileirar(m);
                        posicoes[p] = null;
                        qtdOcupada = qtdOcupada - 1;

                        if (filasEspera[p].vazia() == false) {
                            Minerador sub = filasEspera[p].desenfileirar();
                            posicoes[p] = sub;
                            sub.estado = 0;
                            sub.recemAlocado = true;
                            qtdOcupada = qtdOcupada + 1;
                        }
                    }
                    p = p + 1;
                }

                Fila viajantesAtualizados = new Fila(n);
                
                while (filaViajantes.vazia() == false) {
                    Minerador m = filaViajantes.desenfileirar();
                    m.tempoViagem = m.tempoViagem - 1;

                    if (m.tempoViagem == 0) {
                        if (m.estado == 1) {
                            totalBase = totalBase + m.coletado;
                            System.out.println("minerador " + m.chave + " depositou " + m.coletado + " minerio(s) na base.");
                            m.coletado = 0;
                            m.estado = 2;
                            m.tempoViagem = d;
                            viajantesAtualizados.enfileirar(m);
                        } else if (m.estado == 2) {
                            System.out.println("minerador " + m.chave + " retornou as minas.");
                            
                            int h = m.chave % t;
                            if (h < 0) {
                                h = h + t;
                            }

                            if (qtdOcupada == t) {
                                System.out.println("minerador " + m.chave + " inserido na fila de espera da mina " + h + ".");
                                filasEspera[h].enfileirar(m);
                                m.estado = 3;
                            } else if (posicoes[h] == null) {
                                System.out.println("minerador " + m.chave + " inserido na mina " + h + ".");
                                posicoes[h] = m;
                                qtdOcupada = qtdOcupada + 1;
                                m.estado = 0;
                                m.recemAlocado = true;
                            } else {
                                System.out.println("minerador " + m.chave + " colidiu com minerador " + posicoes[h].chave + " na mina " + h + ".");
                                int pos = (h + 1) % t;
                                while (posicoes[pos] != null) {
                                    pos = (pos + 1) % t;
                                }
                                System.out.println("minerador " + m.chave + " inserido na mina " + pos + ".");
                                posicoes[pos] = m;
                                qtdOcupada = qtdOcupada + 1;
                                m.estado = 0;
                                m.recemAlocado = true;
                            }
                        }
                    } else {
                        viajantesAtualizados.enfileirar(m);
                    }
                }

                while (viajantesNovos.vazia() == false) {
                    viajantesAtualizados.enfileirar(viajantesNovos.desenfileirar());
                }

                filaViajantes = viajantesAtualizados;

                System.out.println("base: " + totalBase + ".");
                turno = turno + 1;
            }

            System.out.println("fim de jogo. " + totalBase + " minerios coletados.");
            System.out.println();
        }
        scanner.close();
    }
}
import java.util.Scanner;

public class L3Q2 {

    static class ListaEncadeada {
        static class No {
            int chave;
            No proximo;

            No(int chave) {
                this.chave = chave;
                this.proximo = null;
            }
        }

        private No inicio;

        ListaEncadeada() {
            this.inicio = null;
        }

        void inserirNoFim(int chave) {
            No novo = new No(chave);
            if (inicio == null) {
                inicio = novo;
                return;
            }
            No atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }

        boolean estaVazia() {
            return inicio == null;
        }

        void imprimirEmLinha() {
            No atual = inicio;
            int pos = 0;
            while (atual != null) {
                if (pos > 0) {
                    System.out.print(" ");
                }
                System.out.print(atual.chave);
                pos = pos + 1;
                atual = atual.proximo;
            }
        }
    }

    static class TabelaHash {
        private int[] tabela;
        private boolean[] ocupado;
        private ListaEncadeada[] listas;
        private int m;
        private int quantidade;

        TabelaHash(int tamanho) {
            this.m = tamanho;
            this.tabela = new int[m];
            this.ocupado = new boolean[m];
            this.listas = new ListaEncadeada[m];
            this.quantidade = 0;
        }

        private int funcaoHash(int chave) {
            int h = chave % m;
            if (h < 0) {
                h = h + m;
            }
            return h;
        }

        public void inserir(int chave) {
            int h = funcaoHash(chave);
            if (quantidade < m) {
                int i = 0;
                int indice = h;
                while (i < m) {
                    if (ocupado[indice] == false) {
                        tabela[indice] = chave;
                        ocupado[indice] = true;
                        quantidade = quantidade + 1;
                        return;
                    }
                    indice = indice + 1;
                    if (indice == m) {
                        indice = 0;
                    }
                    i = i + 1;
                }
            }

            if (listas[h] == null) {
                listas[h] = new ListaEncadeada();
            }
            listas[h].inserirNoFim(chave);
        }

        public void imprimir() {
            int i = 0;
            while (i < m) {
                System.out.print("Posicao " + i + ": ");
                if (ocupado[i]) {
                    System.out.print(tabela[i]);
                } else {
                    System.out.print("NIL");
                }
                if (listas[i] != null && listas[i].estaVazia() == false) {
                    System.out.print(" -> ");
                    listas[i].imprimirEmLinha();
                }
                System.out.println();
                i = i + 1;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Insira o tamanho da tabela: ");
        int m = scanner.nextInt();
        while (m <= 0) {
            System.out.println("ERRO: a capacidade inicial deve ser maior que zero. Informe M novamente:");
            m = scanner.nextInt();
        }

        TabelaHash tabela = new TabelaHash(m);
        int opcao = -1;

        System.out.println();
        System.out.println("=*=*=*=*=*=**=*=*=**=*=*=*=**");
        System.out.println("1 - Inserir valor");
        System.out.println("2 - Visualizar tabela");
        System.out.println("3 - Sair");
        System.out.println("=*=*=*=*=*=**=*=*=**=*=*=*=**");

        while (opcao != 3) {
            System.out.print("Digite uma opcao: ");
            opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Digite o valor para inserir: ");
                tabela.inserir(scanner.nextInt());
            } else if (opcao == 2) {
                tabela.imprimir();
            } else if (opcao == 3) {
                System.out.println("Programa encerrado.");
            } else {
                System.out.println("Opcao invalida. Tente novamente.");
            }
        }

        scanner.close();
    }
}

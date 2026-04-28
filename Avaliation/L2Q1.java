import java.util.Scanner;

public class L2Q1 {

    // dados -> ListSeqOrd, n -> tamanho do vetor, M -> tamanho máximo 
    static class ListaSeqOrd {
        private int[] dados;
        private int n;
        private int M;

        ListaSeqOrd(int capacidadeInicial) {
            this.M = capacidadeInicial;
            this.dados = new int[this.M];
            this.n = 0;
        }

        public void imprimir() {
            int i = 0;
            while (i < M) {
                if (i < n) {
                    System.out.println("Posicao " + i + ": " + dados[i]);
                } else {
                    System.out.println("Posicao " + i + ": NIL");
                }
                i = i + 1;
            }
        }

        public int buscar(int valor) {
            int i = 0;
            while (i < n) {
                if (dados[i] == valor) {
                    return i;
                }
                i = i + 1;
            }
            return -1;
        }

        public void inserir(int valor) {
            if (n == M) {
                int novaCapacidade = M * 2;
                int[] novoArray = new int[novaCapacidade];

                int i = 0;
                while (i < n) {
                    novoArray[i] = dados[i];
                    i = i + 1;
                }

                dados = novoArray;
                M = novaCapacidade;
                System.out.println("Capacidade maxima atingida. Novo tamanho da estrutura: " + M);
            }

            int posicaoInsercao = 0;
            while (posicaoInsercao < n && dados[posicaoInsercao] < valor) {
                posicaoInsercao = posicaoInsercao + 1;
            }

            int i = n;
            while (i > posicaoInsercao) {
                dados[i] = dados[i - 1];
                i = i - 1;
            }

            dados[posicaoInsercao] = valor;
            n = n + 1;
        }

        public void remover(int valor) {
            int posicao = buscar(valor);
            if (posicao == -1) {
                System.out.println("ERRO: valor não encontrado.");
                return;
            }

            int i = posicao;
            while (i < n - 1) {
                dados[i] = dados[i + 1];
                i = i + 1;
            }

            n = n - 1;
            return;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int m = scanner.nextInt();

        while (m <= 0) {
            System.out.println("ERRO: a capacidade inicial deve ser maior que zero. Informe M novamente:");
            m = scanner.nextInt();
        }

        ListaSeqOrd lista = new ListaSeqOrd(m);
        int opcao = -1;

        System.out.println();
        System.out.println("=*=*=*=*=*=**=*=*=**=*=*=*=**");
        System.out.println("1 - Visualizar lista");
        System.out.println("2 - Buscar valor");
        System.out.println("3 - Inserir valor");
        System.out.println("4 - Remover valor");
        System.out.println("5 - Sair");
        System.out.println("=*=*=*=*=*=**=*=*=**=*=*=*=**");

        while (opcao != 5) {
            System.out.print("Digite uma opcao: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    lista.imprimir();
                    break;

                case 2:
                    System.out.print("Digite o valor para buscar: ");
                    int posicao = lista.buscar(scanner.nextInt());
                    System.out.println("Valor encontrado na posicao " + posicao + ".");
                    break;

                case 3:
                    System.out.print("Digite o valor para inserir: ");
                    lista.inserir(scanner.nextInt());
                    break;

                case 4:
                    System.out.print("Digite o valor para remover: ");
                    lista.remover(scanner.nextInt());
                    break;

                case 5:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }
}


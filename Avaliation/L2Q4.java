import java.util.Scanner;

public class L2Q4 {

    static int totalChamadasTrocar = 0;
    static int totalChamadasMaxheapfy = 0;

    public static void imprimir(int[] vetor, int tamanho) {
        int i = 0;
        while (i < tamanho) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(vetor[i]);
            i = i + 1;
        }
        System.out.println();
    }

    public static void trocar(int[] vetor, int i, int j) {
        int auxiliar = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = auxiliar;
        totalChamadasTrocar = totalChamadasTrocar + 1;
    }

    public static void maxheapfy(int[] vetor, int i, int tamanhoHeap, int tamanhoTotal) {
        totalChamadasMaxheapfy = totalChamadasMaxheapfy + 1;
        System.out.println("maxheapfy " + i);

        int maior = i;
        int esquerda = (2 * i) + 1;
        int direita = (2 * i) + 2;

        if (esquerda < tamanhoHeap && vetor[esquerda] > vetor[maior]) {
            maior = esquerda;
        }

        if (direita < tamanhoHeap && vetor[direita] > vetor[maior]) {
            maior = direita;
        }

        if (maior != i) {
            trocar(vetor, i, maior);
            maxheapfy(vetor, maior, tamanhoHeap, tamanhoTotal);
        }

        imprimir(vetor, tamanhoTotal);
    }

    public static void construirMaxHeap(int[] vetor, int tamanho, int tamanhoTotal) {
        int i = (tamanho / 2) - 1;
        while (i >= 0) {
            maxheapfy(vetor, i, tamanho, tamanhoTotal);
            i = i - 1;
        }
    }

    public static void heapsort(int[] vetor, int tamanho, int tamanhoTotal) {
        construirMaxHeap(vetor, tamanho, tamanhoTotal);
        imprimir(vetor, tamanhoTotal);

        int tamanhoHeap = tamanho;
        int i = tamanho - 1;
        while (i >= 1) {
            int auxiliar = vetor[0];
            vetor[0] = vetor[i];
            vetor[i] = auxiliar;

            tamanhoHeap = tamanhoHeap - 1;
            maxheapfy(vetor, 0, tamanhoHeap, tamanhoTotal);
            i = i - 1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insira a capacidade total do vetor: ");

        int m = scanner.nextInt();
        int[] vetor = new int[m];

        int i = 0;
        System.out.print("Insira o vetor: ");
        while (i < m) {
            vetor[i] = scanner.nextInt();
            i = i + 1;
        }

        imprimir(vetor, m);
        heapsort(vetor, m, m);
        System.out.println("Total de chamadas ao trocar: " + totalChamadasTrocar);
        System.out.println("Total de chamadas ao maxheapfy: " + totalChamadasMaxheapfy);

        scanner.close();
    }
}

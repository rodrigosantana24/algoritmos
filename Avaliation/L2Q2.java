import java.util.Scanner;

public class L2Q2 {

    static int totalChamadasMergeSort = 0;

    public static void imprimirVetor(int[] vetor, int tamanho) {
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

    public static void merge(int[] vetor, int p, int q, int r, int tamanhoTotal) {
        System.out.println("merge " + p + " " + q + " " + r);

        int tamanhoEsquerda = q - p + 1;
        int tamanhoDireita = r - q;

        int[] esquerda = new int[tamanhoEsquerda];
        int[] direita = new int[tamanhoDireita];

        int i = 0;
        while (i < tamanhoEsquerda) {
            esquerda[i] = vetor[p + i];
            i = i + 1;
        }

        int j = 0;
        while (j < tamanhoDireita) {
            direita[j] = vetor[q + 1 + j];
            j = j + 1;
        }

        i = 0;
        j = 0;
        int k = p;

        while (i < tamanhoEsquerda && j < tamanhoDireita) {
            if (esquerda[i] <= direita[j]) {
                vetor[k] = esquerda[i];
                i = i + 1;
            } else {
                vetor[k] = direita[j];
                j = j + 1;
            }
            k = k + 1;
        }

        while (i < tamanhoEsquerda) {
            vetor[k] = esquerda[i];
            i = i + 1;
            k = k + 1;
        }

        while (j < tamanhoDireita) {
            vetor[k] = direita[j];
            j = j + 1;
            k = k + 1;
        }

        imprimirVetor(vetor, tamanhoTotal);
    }

    public static void mergesort(int[] vetor, int p, int r, int tamanhoTotal) {
        totalChamadasMergeSort = totalChamadasMergeSort + 1;
        System.out.println("mergesort " + p + " " + r + ": empilhado!");

        if (p < r) {
            int q = (p + r) / 2;
            mergesort(vetor, p, q, tamanhoTotal);
            mergesort(vetor, q + 1, r, tamanhoTotal);
            merge(vetor, p, q, r, tamanhoTotal);
        }

        System.out.println("mergesort " + p + " " + r + ": dsempilhado!");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int m = scanner.nextInt();
        int[] vetor = new int[m];

        int i = 0;
        while (i < m) {
            vetor[i] = scanner.nextInt();
            i = i + 1;
        }

        imprimirVetor(vetor, m);

        if (m > 0) {
            mergesort(vetor, 0, m - 1, m);
        }

        System.out.println("Total de chamadas ao mergesort: " + totalChamadasMergeSort);

        scanner.close();
    }
}

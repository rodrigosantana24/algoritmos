import java.util.Scanner;

public class L2Q3 {

    static int totalChamadasTrocar = 0;

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

    public static int particionar(int[] vetor, int p, int r, int tamanhoTotal) {
        System.out.println("particionar " + p + " " + r);

        int pivot = vetor[r];
        int i = p - 1;
        int j = p;

        while (j <= r - 1) {
            if (vetor[j] <= pivot) {
                i = i + 1;
                trocar(vetor, i, j);
            }
            j = j + 1;
        }

        trocar(vetor, i + 1, r);
        imprimir(vetor, tamanhoTotal);
        return i + 1;
    }

    public static void quicksort(int[] vetor, int p, int r, int tamanhoTotal) {
        System.out.println("quicksort " + p + " " + r + ": empilhado!");

        if (p < r) {
            int q = particionar(vetor, p, r, tamanhoTotal);
            quicksort(vetor, p, q - 1, tamanhoTotal);
            quicksort(vetor, q + 1, r, tamanhoTotal);
        }

        System.out.println("quicksort " + p + " " + r + ": desempilhado!");
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
        quicksort(vetor, 0, m - 1, m);
        System.out.println("Total de chamadas ao trocar: " + totalChamadasTrocar);

        scanner.close();
    }
}

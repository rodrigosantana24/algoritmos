import java.util.Scanner;

class Matriz{
    public static void main(String[] args){     
        Scanner sc = new Scanner(System.in);
        
        final int linhas = 3;
        final int colunas = 5;
        int[][] matriz = new int[linhas][colunas];

        for(int i=0; i<linhas; i++){
            for(int j=0; j<colunas; j++){
                matriz[i][j] = sc.nextInt();
            }
        }

        for(int i=0; i<linhas; i++){
            for(int j=0; j<colunas; j++){
                System.out.println(matriz[i][j]);
            }
        }
        sc.close();
    }
}
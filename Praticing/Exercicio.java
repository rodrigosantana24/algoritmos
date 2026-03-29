public class Exercicio {
    public static void main(String[] args){
        /* Implemente um algoritmo que receba duas matrizes 
           Anxm e Bsxp, e verifique se B é a matriz transposta de A.  */

        int[][] A = { {1, 2, 3}, {4, 5, 6} };
        int[][] B = { {1, 4}, {2, 5}, {3, 6} };

        boolean response = ehTransposta(B, A);

        if(response){
            System.out.println("É Transposta.");
        }
        else {
            System.out.println("Não é Transposta.");
        }
    }

    public static boolean ehTransposta(int[][] B, int[][] A){
        if(B.length != A[0].length || B[0].length != A.length){
            return false;
        }

        for(int i=0; i<A.length; i++){
            for(int j=0; j<A[0].length; j++){
                if(A[i][j] != B[j][i]){
                    return false;
                }
            }
        } 
        return true;
    }
}

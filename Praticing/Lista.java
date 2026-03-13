package Praticing;

public class Lista {
    public static void main(String[] args){
        int[] listaEstranha = new int[]{8, 32, 0, 11};
        int elemento = 8;

        Integer indice = busca(listaEstranha, elemento);

        if(indice != null){
            System.out.println("O índice do elemento é: " + indice);
        } else {
            System.out.println("O elemento: " + elemento + " não foi encontrado.");
        }

    }

    public static Integer busca(int[] lista, int valor){
        for(int i=0; i<lista.length; i++){
            if(lista[i] == valor){
                return i;
            }
        }
        return null;
    }   
}

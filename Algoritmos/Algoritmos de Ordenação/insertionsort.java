class InsertionSort {
    public static void main(String[] args){
        int[] numbers = {9, 5, 6, 2, 8, 1, 7, 11, 25, 4};

        System.out.print("Antes: ");
        printArray(numbers);
        
        System.out.println();

        System.out.print("Depois: ");
        insertionSort(numbers);
        printArray(numbers);

    }

    public static void insertionSort(int[] numbers){
        for(int i=1; i<numbers.length; i++){
            int valorAtual = numbers[i];

            int j = i-1;
            
            while(j>=0 && numbers[j] > valorAtual){
                numbers[j+1] = numbers[j];
                j--;
            }
            numbers[j+1] = valorAtual;
        }
    } 

    public static void printArray(int[] numbers){
        for(int i=0; i<numbers.length; i++){
            System.out.print(numbers[i]);
        }
    }
}
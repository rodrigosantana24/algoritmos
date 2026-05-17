import java.util.Scanner;

public class P01 {

    static class Lutador {
        String identificador;
        int time;
        int dano;
        int pontosVida;
        int iniciativaBase;
        boolean atacouNoTurno;

        Lutador(String identificador, int time, int dano, int pontosVida, int iniciativaBase) {
            this.identificador = identificador;
            this.time = time;
            this.dano = dano;
            this.pontosVida = pontosVida;
            this.iniciativaBase = iniciativaBase;
            this.atacouNoTurno = false;
        }
    }

    static class ListaLutadores {
        private Lutador[] dados;
        private int tamanho;

        ListaLutadores(int capacidadeInicial) {
            if (capacidadeInicial < 1) {
                capacidadeInicial = 1;
            }
            this.dados = new Lutador[capacidadeInicial];
            this.tamanho = 0;
        }

        private void garantirCapacidade() {
            if (tamanho < dados.length) {
                return;
            }

            Lutador[] novoArray = new Lutador[dados.length * 2];
            int i = 0;
            while (i < tamanho) {
                novoArray[i] = dados[i];
                i = i + 1;
            }
            dados = novoArray;
        }

        int tamanho() {
            return tamanho;
        }

        Lutador obter(int indice) {
            return dados[indice];
        }

        void inserirNoFim(Lutador lutador) {
            garantirCapacidade();
            dados[tamanho] = lutador;
            tamanho = tamanho + 1;
        }

        boolean contemId(String identificador) {
            int i = 0;
            while (i < tamanho) {
                if (dados[i].identificador.equals(identificador)) {
                    return true;
                }
                i = i + 1;
            }
            return false;
        }

        Lutador removerPorId(String identificador) {
            int i = 0;
            while (i < tamanho) {
                if (dados[i].identificador.equals(identificador)) {
                    return removerPorIndice(i);
                }
                i = i + 1;
            }
            return null;
        }

        boolean removerPorReferencia(Lutador lutador) {
            int i = 0;
            while (i < tamanho) {
                if (dados[i] == lutador) {
                    removerPorIndice(i);
                    return true;
                }
                i = i + 1;
            }
            return false;
        }

        private Lutador removerPorIndice(int indice) {
            Lutador removido = dados[indice];
            int i = indice;
            while (i < tamanho - 1) {
                dados[i] = dados[i + 1];
                i = i + 1;
            }
            dados[tamanho - 1] = null;
            tamanho = tamanho - 1;
            return removido;
        }

        Lutador[] copiarParaArray() {
            Lutador[] copia = new Lutador[tamanho];
            int i = 0;
            while (i < tamanho) {
                copia[i] = dados[i];
                i = i + 1;
            }
            return copia;
        }
    }

    static class ListaEncadeadaLutadores {
        static class No {
            Lutador valor;
            No proximo;

            No(Lutador valor) {
                this.valor = valor;
                this.proximo = null;
            }
        }

        private No inicio;
        private int tamanho;

        ListaEncadeadaLutadores() {
            this.inicio = null;
            this.tamanho = 0;
        }

        int tamanho() {
            return tamanho;
        }

        boolean contemId(String identificador) {
            No atual = inicio;
            while (atual != null) {
                if (atual.valor.identificador.equals(identificador)) {
                    return true;
                }
                atual = atual.proximo;
            }
            return false;
        }

        void inserirOrdenadoPorIniciativaDesc(Lutador lutador) {
            No novo = new No(lutador);

            if (inicio == null || lutador.iniciativaBase > inicio.valor.iniciativaBase) {
                novo.proximo = inicio;
                inicio = novo;
                tamanho = tamanho + 1;
                return;
            }

            No atual = inicio;
            while (atual.proximo != null && atual.proximo.valor.iniciativaBase >= lutador.iniciativaBase) {
                atual = atual.proximo;
            }

            novo.proximo = atual.proximo;
            atual.proximo = novo;
            tamanho = tamanho + 1;
        }

        Lutador[] copiarParaArray() {
            Lutador[] array = new Lutador[tamanho];
            No atual = inicio;
            int i = 0;
            while (atual != null) {
                array[i] = atual.valor;
                atual = atual.proximo;
                i = i + 1;
            }
            return array;
        }
    }

    static class FilaLutadores {
        private Lutador[] dados;
        private int inicio;
        private int fim;
        private int quantidade;

        FilaLutadores(int capacidadeInicial) {
            if (capacidadeInicial < 2) {
                capacidadeInicial = 2;
            }
            this.dados = new Lutador[capacidadeInicial];
            this.inicio = 0;
            this.fim = 0;
            this.quantidade = 0;
        }

        private int proximoIndice(int indice) {
            int proximo = indice + 1;
            if (proximo == dados.length) {
                proximo = 0;
            }
            return proximo;
        }

        private void expandir() {
            Lutador[] novoArray = new Lutador[dados.length * 2];
            int i = 0;
            int indice = inicio;
            while (i < quantidade) {
                novoArray[i] = dados[indice];
                indice = proximoIndice(indice);
                i = i + 1;
            }
            dados = novoArray;
            inicio = 0;
            fim = quantidade;
        }

        void enfileirar(Lutador lutador) {
            if (quantidade == dados.length - 1) {
                expandir();
            }
            dados[fim] = lutador;
            fim = proximoIndice(fim);
            quantidade = quantidade + 1;
        }

        Lutador desenfileirar() {
            if (quantidade == 0) {
                return null;
            }

            Lutador removido = dados[inicio];
            dados[inicio] = null;
            inicio = proximoIndice(inicio);
            quantidade = quantidade - 1;
            return removido;
        }

        boolean estaVazia() {
            return quantidade == 0;
        }

        int quantidade() {
            return quantidade;
        }
    }

    static class AlgoritmosOrdenacao {

        private static void trocar(Lutador[] vetor, int i, int j) {
            Lutador aux = vetor[i];
            vetor[i] = vetor[j];
            vetor[j] = aux;
        }

        static void mergeSortDecrescente(Lutador[] vetor, int tamanho) {
            if (tamanho <= 1) {
                return;
            }
            Lutador[] auxiliar = new Lutador[tamanho];
            mergeSortRec(vetor, auxiliar, 0, tamanho - 1);
        }

        private static void mergeSortRec(Lutador[] vetor, Lutador[] auxiliar, int esquerda, int direita) {
            if (esquerda >= direita) {
                return;
            }

            int meio = (esquerda + direita) / 2;
            mergeSortRec(vetor, auxiliar, esquerda, meio);
            mergeSortRec(vetor, auxiliar, meio + 1, direita);
            merge(vetor, auxiliar, esquerda, meio, direita);
        }

        private static void merge(Lutador[] vetor, Lutador[] auxiliar, int esquerda, int meio, int direita) {
            int i = esquerda;
            int j = meio + 1;
            int k = esquerda;

            while (i <= meio && j <= direita) {
                if (vetor[i].iniciativaBase >= vetor[j].iniciativaBase) {
                    auxiliar[k] = vetor[i];
                    i = i + 1;
                } else {
                    auxiliar[k] = vetor[j];
                    j = j + 1;
                }
                k = k + 1;
            }

            while (i <= meio) {
                auxiliar[k] = vetor[i];
                i = i + 1;
                k = k + 1;
            }

            while (j <= direita) {
                auxiliar[k] = vetor[j];
                j = j + 1;
                k = k + 1;
            }

            int pos = esquerda;
            while (pos <= direita) {
                vetor[pos] = auxiliar[pos];
                pos = pos + 1;
            }
        }

        static void quickSortDecrescente(Lutador[] vetor, int tamanho) {
            if (tamanho <= 1) {
                return;
            }
            quickSortRec(vetor, 0, tamanho - 1);
        }

        private static void quickSortRec(Lutador[] vetor, int esquerda, int direita) {
            if (esquerda < direita) {
                int pivo = particionar(vetor, esquerda, direita);
                quickSortRec(vetor, esquerda, pivo - 1);
                quickSortRec(vetor, pivo + 1, direita);
            }
        }

        private static int particionar(Lutador[] vetor, int esquerda, int direita) {
            int iniciativaPivo = vetor[direita].iniciativaBase;
            int i = esquerda - 1;
            int j = esquerda;

            while (j <= direita - 1) {
                if (vetor[j].iniciativaBase >= iniciativaPivo) {
                    i = i + 1;
                    trocar(vetor, i, j);
                }
                j = j + 1;
            }

            trocar(vetor, i + 1, direita);
            return i + 1;
        }

        static void heapSortDecrescente(Lutador[] vetor, int tamanho) {
            if (tamanho <= 1) {
                return;
            }

            int i = (tamanho / 2) - 1;
            while (i >= 0) {
                minHeapify(vetor, i, tamanho);
                i = i - 1;
            }

            int fim = tamanho - 1;
            while (fim >= 1) {
                trocar(vetor, 0, fim);
                minHeapify(vetor, 0, fim);
                fim = fim - 1;
            }
        }

        private static void minHeapify(Lutador[] vetor, int indice, int tamanhoHeap) {
            int menor = indice;
            int esquerdo = (2 * indice) + 1;
            int direito = (2 * indice) + 2;

            if (esquerdo < tamanhoHeap && vetor[esquerdo].iniciativaBase < vetor[menor].iniciativaBase) {
                menor = esquerdo;
            }

            if (direito < tamanhoHeap && vetor[direito].iniciativaBase < vetor[menor].iniciativaBase) {
                menor = direito;
            }

            if (menor != indice) {
                trocar(vetor, indice, menor);
                minHeapify(vetor, menor, tamanhoHeap);
            }
        }
    }

    static class Time {
        private int numero;
        private ListaLutadores vivos;
        private ListaEncadeadaLutadores mortos;

        Time(int numero, int capacidadeInicial) {
            this.numero = numero;
            this.vivos = new ListaLutadores(capacidadeInicial);
            this.mortos = new ListaEncadeadaLutadores();
        }

        int getNumero() {
            return numero;
        }

        boolean contemId(String identificador) {
            return vivos.contemId(identificador) || mortos.contemId(identificador);
        }

        void inserirLutadorVivo(Lutador lutador) {
            vivos.inserirNoFim(lutador);
        }

        Lutador removerVivoPorId(String identificador) {
            return vivos.removerPorId(identificador);
        }

        int quantidadeVivos() {
            return vivos.tamanho();
        }

        int quantidadeMortos() {
            return mortos.tamanho();
        }

        void registrarMorte(Lutador lutador) {
            vivos.removerPorReferencia(lutador);
            mortos.inserirOrdenadoPorIniciativaDesc(lutador);
        }

        void resetarAtaquesDoTurno() {
            int i = 0;
            while (i < vivos.tamanho()) {
                vivos.obter(i).atacouNoTurno = false;
                i = i + 1;
            }
        }

        FilaLutadores criarFilaCombateOrdenada() {
            Lutador[] ordenados = vivos.copiarParaArray();
            AlgoritmosOrdenacao.heapSortDecrescente(ordenados, ordenados.length);

            FilaLutadores fila = new FilaLutadores(ordenados.length + 1);
            int i = 0;
            while (i < ordenados.length) {
                fila.enfileirar(ordenados[i]);
                i = i + 1;
            }
            return fila;
        }

        void imprimirRelatorio() {
            System.out.println("Time " + numero);
            System.out.println("Lutadores vivos: " + vivos.tamanho());
            System.out.println("Lutadores mortos: " + mortos.tamanho());

            Lutador[] vivosOrdenados = vivos.copiarParaArray();
            AlgoritmosOrdenacao.mergeSortDecrescente(vivosOrdenados, vivosOrdenados.length);

            Lutador[] mortosOrdenados = mortos.copiarParaArray();
            AlgoritmosOrdenacao.quickSortDecrescente(mortosOrdenados, mortosOrdenados.length);

            System.out.println("Vivos (ID, iniciativa, vida):");
            if (vivosOrdenados.length == 0) {
                System.out.println("NENHUM");
            } else {
                int i = 0;
                while (i < vivosOrdenados.length) {
                    Lutador lutador = vivosOrdenados[i];
                    System.out.println(lutador.identificador + " " + lutador.iniciativaBase + " " + lutador.pontosVida);
                    i = i + 1;
                }
            }

            System.out.println("Mortos (ID, iniciativa, vida):");
            if (mortosOrdenados.length == 0) {
                System.out.println("NENHUM");
            } else {
                int i = 0;
                while (i < mortosOrdenados.length) {
                    Lutador lutador = mortosOrdenados[i];
                    System.out.println(lutador.identificador + " " + lutador.iniciativaBase + " " + lutador.pontosVida);
                    i = i + 1;
                }
            }
        }
    }

    static class Jogo {
        private Scanner scanner;
        private Time time1;
        private Time time2;
        private int turnoAtual;
        private boolean jogoEncerrado;

        Jogo() {
            this.scanner = new Scanner(System.in);
            this.time1 = new Time(1, 4);
            this.time2 = new Time(2, 4);
            this.turnoAtual = 1;
            this.jogoEncerrado = false;
        }

        private int lerInteiro() {
            while (!scanner.hasNextInt()) {
                System.out.println("ERRO: informe um numero inteiro.");
                scanner.next();
            }
            return scanner.nextInt();
        }

        private boolean idJaExiste(String identificador) {
            return time1.contemId(identificador) || time2.contemId(identificador);
        }

        private void inserirLutador() {
            System.out.print("Identificador: ");
            String identificador = scanner.next();

            if (idJaExiste(identificador)) {
                System.out.println("ERRO: identificador ja existente.");
                return;
            }

            System.out.print("Time (1 ou 2): ");
            int numeroTime = lerInteiro();
            if (numeroTime != 1 && numeroTime != 2) {
                System.out.println("ERRO: time invalido.");
                return;
            }

            System.out.print("Dano: ");
            int dano = lerInteiro();
            if (dano <= 0) {
                System.out.println("ERRO: dano deve ser maior que zero.");
                return;
            }

            System.out.print("Pontos de vida: ");
            int vida = lerInteiro();
            if (vida <= 0) {
                System.out.println("ERRO: pontos de vida devem ser maiores que zero.");
                return;
            }

            System.out.print("Iniciativa (1 a 100): ");
            int iniciativa = lerInteiro();
            if (iniciativa < 1 || iniciativa > 100) {
                System.out.println("ERRO: iniciativa fora do intervalo permitido.");
                return;
            }

            Lutador lutador = new Lutador(identificador, numeroTime, dano, vida, iniciativa);
            if (numeroTime == 1) {
                time1.inserirLutadorVivo(lutador);
            } else {
                time2.inserirLutadorVivo(lutador);
            }

            System.out.println("Lutador inserido com sucesso.");
        }

        private void relatorioDeTime() {
            System.out.print("Informe o time (1 ou 2): ");
            int numeroTime = lerInteiro();

            if (numeroTime == 1) {
                time1.imprimirRelatorio();
            } else if (numeroTime == 2) {
                time2.imprimirRelatorio();
            } else {
                System.out.println("ERRO: time invalido.");
            }
        }

        private void fugaDeLutador() {
            System.out.print("Identificador do lutador que deseja fugir: ");
            String identificador = scanner.next();

            Lutador removido = time1.removerVivoPorId(identificador);
            if (removido == null) {
                removido = time2.removerVivoPorId(identificador);
            }

            if (removido == null) {
                System.out.println("ERRO: lutador nao encontrado entre os vivos.");
            } else {
                System.out.println("Lutador removido do combate.");
            }
        }

        private void faseOrganizacao() {
            boolean iniciarCombate = false;

            while (!iniciarCombate && !jogoEncerrado) {
                System.out.println();
                System.out.println("==== TURNO " + turnoAtual + " - FASE DE ORGANIZACAO ====");
                System.out.println("1 - Inserir lutador");
                System.out.println("2 - Relatorio de um time");
                System.out.println("3 - Fuga de lutador");
                System.out.println("4 - Iniciar combate do turno");
                System.out.println("5 - Encerrar jogo");
                System.out.print("Opcao: ");

                int opcao = lerInteiro();

                if (opcao == 1) {
                    inserirLutador();
                } else if (opcao == 2) {
                    relatorioDeTime();
                } else if (opcao == 3) {
                    fugaDeLutador();
                } else if (opcao == 4) {
                    iniciarCombate = true;
                } else if (opcao == 5) {
                    jogoEncerrado = true;
                    System.out.println("Jogo encerrado por comando do usuario.");
                } else {
                    System.out.println("ERRO: opcao invalida.");
                }
            }
        }

        private void faseCombate() {
            System.out.println();
            System.out.println("==== FASE DE COMBATE ====");

            time1.resetarAtaquesDoTurno();
            time2.resetarAtaquesDoTurno();

            FilaLutadores filaTime1 = time1.criarFilaCombateOrdenada();
            FilaLutadores filaTime2 = time2.criarFilaCombateOrdenada();

            int totalInicialTime1 = filaTime1.quantidade();
            int totalInicialTime2 = filaTime2.quantidade();
            int atacaramTime1 = 0;
            int atacaramTime2 = 0;

            while (!filaTime1.estaVazia() && !filaTime2.estaVazia()) {
                if (atacaramTime1 >= totalInicialTime1 && atacaramTime2 >= totalInicialTime2) {
                    break;
                }

                Lutador lutador1 = filaTime1.desenfileirar();
                Lutador lutador2 = filaTime2.desenfileirar();

                boolean lutador1PodeAtacar = !lutador1.atacouNoTurno;
                boolean lutador2PodeAtacar = !lutador2.atacouNoTurno;

                int danoEmLutador1 = 0;
                int danoEmLutador2 = 0;

                if (lutador1PodeAtacar) {
                    danoEmLutador2 = lutador1.dano;
                    lutador1.atacouNoTurno = true;
                    atacaramTime1 = atacaramTime1 + 1;
                }

                if (lutador2PodeAtacar) {
                    danoEmLutador1 = lutador2.dano;
                    lutador2.atacouNoTurno = true;
                    atacaramTime2 = atacaramTime2 + 1;
                }

                lutador1.pontosVida = lutador1.pontosVida - danoEmLutador1;
                lutador2.pontosVida = lutador2.pontosVida - danoEmLutador2;

                if (lutador1.pontosVida > 0) {
                    filaTime1.enfileirar(lutador1);
                } else {
                    time1.registrarMorte(lutador1);
                }

                if (lutador2.pontosVida > 0) {
                    filaTime2.enfileirar(lutador2);
                } else {
                    time2.registrarMorte(lutador2);
                }
            }
        }

        private void declararVencedor(int numeroTime, String motivo) {
            System.out.println("VENCEDOR: Time " + numeroTime + ". Motivo: " + motivo);
            jogoEncerrado = true;
        }

        private void declararEmpate() {
            System.out.println("RESULTADO: EMPATE.");
            jogoEncerrado = true;
        }

        private void faseResultados() {
            System.out.println();
            System.out.println("==== FASE DE RESULTADOS ====");

            int vivosTime1 = time1.quantidadeVivos();
            int vivosTime2 = time2.quantidadeVivos();
            int scoreTime1 = time2.quantidadeMortos();
            int scoreTime2 = time1.quantidadeMortos();

            System.out.println("Score Time 1: " + scoreTime1);
            System.out.println("Score Time 2: " + scoreTime2);
            System.out.println("Vivos Time 1: " + vivosTime1);
            System.out.println("Vivos Time 2: " + vivosTime2);

            if (vivosTime1 > 0 && vivosTime2 == 0) {
                declararVencedor(1, "time adversario sem lutadores vivos");
                return;
            }

            if (vivosTime2 > 0 && vivosTime1 == 0) {
                declararVencedor(2, "time adversario sem lutadores vivos");
                return;
            }

            if (vivosTime1 > 0 && vivosTime2 > 0) {
                boolean time1ScoreMinimo = scoreTime1 >= 20;
                boolean time2ScoreMinimo = scoreTime2 >= 20;

                if (time1ScoreMinimo && !time2ScoreMinimo) {
                    declararVencedor(1, "score maior ou igual a 20");
                    return;
                }

                if (time2ScoreMinimo && !time1ScoreMinimo) {
                    declararVencedor(2, "score maior ou igual a 20");
                    return;
                }

                if (scoreTime1 > 20 && scoreTime2 > 20) {
                    if (scoreTime1 > scoreTime2) {
                        declararVencedor(1, "ambos score > 20, maior score");
                        return;
                    }

                    if (scoreTime2 > scoreTime1) {
                        declararVencedor(2, "ambos score > 20, maior score");
                        return;
                    }
                }
            }

            if (vivosTime1 == 0 && vivosTime2 == 0) {
                if (scoreTime1 > scoreTime2) {
                    declararVencedor(1, "ambos vazios e maior score");
                    return;
                }

                if (scoreTime2 > scoreTime1) {
                    declararVencedor(2, "ambos vazios e maior score");
                    return;
                }

                declararEmpate();
                return;
            }

            System.out.println("Nenhuma condicao de termino atingida. Proximo turno.");
        }

        void executar() {
            while (!jogoEncerrado) {
                faseOrganizacao();
                if (jogoEncerrado) {
                    break;
                }

                faseCombate();
                faseResultados();
                turnoAtual = turnoAtual + 1;
            }

            scanner.close();
        }
    }

    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.executar();
    }
}

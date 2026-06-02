import java.util.Scanner;

public class L3Q1 {

    static class No {
        int chave;
        No esquerda;
        No direita;

        No(int valor) {
            this.chave = valor;
            this.esquerda = null;
            this.direita = null;
        }
    }

    static class ArvoreBinaria {
        private No raiz;

        ArvoreBinaria() {
            this.raiz = null;
        }

        public void inserir(int valor) {
            No novo = new No(valor);
            if (raiz == null) {
                raiz = novo;
            } else {
                No atual = raiz;
                while (true) {
                    No anterior = atual;
                    if (valor <= atual.chave) {
                        atual = atual.esquerda;
                        if (atual == null) {
                            anterior.esquerda = novo;
                            return;
                        }
                    } else {
                        atual = atual.direita;
                        if (atual == null) {
                            anterior.direita = novo;
                            return;
                        }
                    }
                }
            }
        }

        public No buscar(int chave) {
            if (raiz == null) {
                return null;
            }
            No atual = raiz;
            while (atual.chave != chave) {
                if (chave < atual.chave) {
                    atual = atual.esquerda;
                } else {
                    atual = atual.direita;
                }
                if (atual == null) {
                    return null;
                }
            }
            return atual;
        }

        public No sucessor(No apaga) {
            No paiDoSucessor = apaga;
            No sucessor = apaga;
            No atual = apaga.direita;
            while (atual != null) {
                paiDoSucessor = sucessor;
                sucessor = atual;
                atual = atual.esquerda;
            }
            if (sucessor != apaga.direita) {
                paiDoSucessor.esquerda = sucessor.direita;
                sucessor.direita = apaga.direita;
            }
            return sucessor;
        }

        public boolean remover(int valor) {
            if (raiz == null) {
                return false;
            }
            No atual = raiz;
            No pai = raiz;
            boolean filhoEsquerdo = true;
            while (atual.chave != valor) {
                pai = atual;
                if (valor < atual.chave) {
                    filhoEsquerdo = true;
                    atual = atual.esquerda;
                } else {
                    filhoEsquerdo = false;
                    atual = atual.direita;
                }
                if (atual == null) {
                    return false;
                }
            }

            if (atual.esquerda == null && atual.direita == null) {
                if (atual == raiz) {
                    raiz = null;
                } else {
                    if (filhoEsquerdo) {
                        pai.esquerda = null;
                    } else {
                        pai.direita = null;
                    }
                }
            } else if (atual.direita == null) {
                if (atual == raiz) {
                    raiz = atual.esquerda;
                } else {
                    if (filhoEsquerdo) {
                        pai.esquerda = atual.esquerda;
                    } else {
                        pai.direita = atual.esquerda;
                    }
                }
            } else if (atual.esquerda == null) {
                if (atual == raiz) {
                    raiz = atual.direita;
                } else {
                    if (filhoEsquerdo) {
                        pai.esquerda = atual.direita;
                    } else {
                        pai.direita = atual.direita;
                    }
                }
            } else {
                No sucessor = sucessor(atual);
                if (atual == raiz) {
                    raiz = sucessor;
                } else {
                    if (filhoEsquerdo) {
                        pai.esquerda = sucessor;
                    } else {
                        pai.direita = sucessor;
                    }
                }
                sucessor.esquerda = atual.esquerda;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArvoreBinaria arvore = new ArvoreBinaria();

        System.out.println("Programa Arvore Binaria");
        int opcao = 0;
        while (opcao != 4) {
            System.out.println("***********************************");
            System.out.println("Entre com a opcao:");
            System.out.println(" --- 1: Inserir");
            System.out.println(" --- 2: Excluir");
            System.out.println(" --- 3: Pesquisar");
            System.out.println(" --- 4: Sair do programa");
            System.out.println("***********************************");
            System.out.print("-> ");
            opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print(" Informe o valor -> ");
                int valor = scanner.nextInt();
                arvore.inserir(valor);
            } else if (opcao == 2) {
                System.out.print(" Informe o valor -> ");
                int valor = scanner.nextInt();
                if (arvore.remover(valor) == false) {
                    System.out.println(" Valor nao encontrado!");
                }
            } else if (opcao == 3) {
                System.out.print(" Informe o valor -> ");
                int valor = scanner.nextInt();
                if (arvore.buscar(valor) != null) {
                    System.out.println(" Valor Encontrado");
                } else {
                    System.out.println(" Valor nao encontrado!");
                }
            } else if (opcao == 4) {
                break;
            } else {
                System.out.println("Opcao invalida. Tente novamente.");
            }
        }

        scanner.close();
    }
}

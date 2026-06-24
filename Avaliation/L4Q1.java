import java.util.Scanner;

public class L4Q1 {

    static class No {
        int chave;
        No esquerda;
        No direita;
        No pai;
        int altura;

        No(int chave) {
            this.chave = chave;
            this.altura = 1;
        }
    }

    static class ArvoreAVL {
        private No raiz;

        ArvoreAVL() {
            this.raiz = null;
        }

        private int alt(No no) {
            if (no == null) {
                return 0;
            }
            return no.altura;
        }

        private int max(int a, int b) {
            if (a > b) {
                return a;
            }
            return b;
        }

        private int abs(int a) {
            if (a < 0) {
                return -a;
            }
            return a;
        }

        private void recalcularAlturasAcima(No no) {
            while (no != null) {
                no.altura = 1 + max(alt(no.esquerda), alt(no.direita));
                no = no.pai;
            }
        }

        private String formatFb(int fb) {
            if (fb > 0) {
                return "+" + fb;
            }
            return String.valueOf(fb);
        }

        private void emOrdem(No no) {
            if (no != null) {
                emOrdem(no.esquerda);
                int fb = alt(no.direita) - alt(no.esquerda);
                System.out.print(no.chave + "(" + formatFb(fb) + ") ");
                emOrdem(no.direita);
            }
        }

        private No rotacaoDireita(No y) {
            No x = y.esquerda;
            No T2 = x.direita;

            x.direita = y;
            y.esquerda = T2;

            if (T2 != null) {
                T2.pai = y;
            }
            x.pai = y.pai;

            if (y.pai == null) {
                raiz = x;
            } else if (y == y.pai.esquerda) {
                y.pai.esquerda = x;
            } else {
                y.pai.direita = x;
            }
            y.pai = x;

            y.altura = 1 + max(alt(y.esquerda), alt(y.direita));
            x.altura = 1 + max(alt(x.esquerda), alt(x.direita));

            return x;
        }

        private No rotacaoEsquerda(No x) {
            No y = x.direita;
            No T2 = y.esquerda;

            y.esquerda = x;
            x.direita = T2;

            if (T2 != null) {
                T2.pai = x;
            }
            y.pai = x.pai;

            if (x.pai == null) {
                raiz = y;
            } else if (x == x.pai.esquerda) {
                x.pai.esquerda = y;
            } else {
                x.pai.direita = y;
            }
            x.pai = y;

            x.altura = 1 + max(alt(x.esquerda), alt(x.direita));
            y.altura = 1 + max(alt(y.esquerda), alt(y.direita));

            return y;
        }

        private No rotacaoDireitaDupla(No responsavel) {
            rotacaoEsquerda(responsavel.esquerda);
            return rotacaoDireita(responsavel);
        }

        private No rotacaoEsquerdaDupla(No responsavel) {
            rotacaoDireita(responsavel.direita);
            return rotacaoEsquerda(responsavel);
        }

        public void inserir(int chave) {
            No novo = new No(chave);
            if (raiz == null) {
                raiz = novo;
                System.out.println("arvore ja balanceada.");
                emOrdem(raiz);
                System.out.println();
                System.out.println(alt(raiz));
                return;
            }

            No atual = raiz;
            No pai = null;
            while (atual != null) {
                pai = atual;
                if (chave < atual.chave) {
                    atual = atual.esquerda;
                } else if (chave > atual.chave) {
                    atual = atual.direita;
                } else {
                    return; 
                }
            }

            novo.pai = pai;
            if (chave < pai.chave) {
                pai.esquerda = novo;
            } else {
                pai.direita = novo;
            }

            recalcularAlturasAcima(novo);

            atual = novo;
            No responsavel = null;
            while (atual != null) {
                int fb = alt(atual.direita) - alt(atual.esquerda);
                if (abs(fb) > 1) {
                    responsavel = atual;
                    break;
                }
                atual = atual.pai;
            }

            if (responsavel == null) {
                System.out.println("arvore ja balanceada.");
                emOrdem(raiz);
                System.out.println();
                System.out.println(alt(raiz));
            } else {
                System.out.println("no responsavel: " + responsavel.chave);
                emOrdem(raiz);
                System.out.println();

                int fbResp = alt(responsavel.direita) - alt(responsavel.esquerda);
                No novaRaizSub = null;

                if (fbResp == -2) {
                    int fbEsq = alt(responsavel.esquerda.direita) - alt(responsavel.esquerda.esquerda);
                    if (fbEsq <= 0) {
                        System.out.println("rotacao direita.");
                        novaRaizSub = rotacaoDireita(responsavel);
                    } else {
                        System.out.println("rotacao direita dupla.");
                        novaRaizSub = rotacaoDireitaDupla(responsavel);
                    }
                } else if (fbResp == 2) {
                    int fbDir = alt(responsavel.direita.direita) - alt(responsavel.direita.esquerda);
                    if (fbDir >= 0) {
                        System.out.println("rotacao esquerda.");
                        novaRaizSub = rotacaoEsquerda(responsavel);
                    } else {
                        System.out.println("rotacao esquerda dupla.");
                        novaRaizSub = rotacaoEsquerdaDupla(responsavel);
                    }
                }

                recalcularAlturasAcima(novaRaizSub);

                emOrdem(raiz);
                System.out.println();
                System.out.println(alt(raiz));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean primeiroConjunto = true;

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine().trim();
            if (linha.isEmpty()) {
                continue;
            }

            if (!primeiroConjunto) {
                System.out.println();
            }
            primeiroConjunto = false;

            String[] partes = linha.split("\\s+");
            ArvoreAVL arvore = new ArvoreAVL();

            int i = 0;
            while (i < partes.length) {
                int chave = Integer.parseInt(partes[i]);
                arvore.inserir(chave);
                i = i + 1;
            }
        }

        if (!primeiroConjunto) {
            System.out.println();
            System.out.println();
        }

        scanner.close();
    }
}
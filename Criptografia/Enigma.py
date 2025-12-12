from typing import List

ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

class Rotor:
    def __init__(self, fiação: str, entalhe: str, nome: str):
        self.fiação = fiação
        self.entalhe = entalhe
        self.nome = nome

        # fiação inversa para caminho de retorno
        self.fiação_inversa = ''.join(
            ALFABETO[self.fiação.index(c)] for c in ALFABETO
        )

        self.posição = 0

    def definir_posição(self, pos_1a26: int):
        self.posição = (pos_1a26 - 1) % 26

    def avançar(self):
        self.posição = (self.posição + 1) % 26

    def está_no_entalhe(self) -> bool:
        return ALFABETO[self.posição] == self.entalhe

    def caminho_ida(self, letra: str) -> str:
        índice = ALFABETO.index(letra)
        índice_deslocado = (índice + self.posição) % 26
        letra_fiada = self.fiação[índice_deslocado]
        índice_saida = (ALFABETO.index(letra_fiada) - self.posição) % 26
        return ALFABETO[índice_saida]

    def caminho_volta(self, letra: str) -> str:
        índice = ALFABETO.index(letra)
        índice_deslocado = (índice + self.posição) % 26
        letra_fiada = self.fiação_inversa[índice_deslocado]
        índice_saida = (ALFABETO.index(letra_fiada) - self.posição) % 26
        return ALFABETO[índice_saida]


class Refletor:
    def __init__(self, fiação: str):
        self.fiação = fiação

    def refletir(self, letra: str) -> str:
        return self.fiação[ALFABETO.index(letra)]


class MaquinaEnigma:
    def __init__(self, rotores: List[Rotor], refletor: Refletor):
        self.rotores = rotores  # [esquerda, meio, direita]
        self.refletor = refletor

    def avançar_rotores(self):
        esquerdo, meio, direito = self.rotores

        # Regras do double-stepping
        if meio.está_no_entalhe():
            esquerdo.avançar()
            meio.avançar()

        elif direito.está_no_entalhe():
            meio.avançar()

        direito.avançar()

    def cifrar_caractere(self, ch: str) -> str:
        if ch not in ALFABETO:
            raise ValueError("A mensagem deve conter apenas A-Z.")

        self.avançar_rotores()

        sinal = self.rotores[2].caminho_ida(ch)
        sinal = self.rotores[1].caminho_ida(sinal)
        sinal = self.rotores[0].caminho_ida(sinal)

        sinal = self.refletor.refletir(sinal)

        sinal = self.rotores[0].caminho_volta(sinal)
        sinal = self.rotores[1].caminho_volta(sinal)
        sinal = self.rotores[2].caminho_volta(sinal)

        return sinal

    def cifrar_mensagem(self, mensagem: str) -> str:
        mensagem = mensagem.upper()
        saída = []

        for ch in mensagem:
            if ch in ALFABETO:
                saída.append(self.cifrar_caractere(ch))

        return ''.join(saída)

# fonte: wikipedia
ROTORES_PADRÃO = {
    1: ("EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q", "I"),
    2: ("AJDKSIRUXBLHWTMCQGZNPYFVOE", "E", "II"),
    3: ("BDFHJLCPRTXVZNYEIWGAKMUSQO", "V", "III"),
    4: ("ESOVPZJAYQUIRHXLNFTGKDCMWB", "J", "IV"),
    5: ("VZBRGITYUPSDNHLXAWMJQOFECK", "Z", "V"),
}

REFLETOR_B = "YRUHQSLDPXNGOKMIEBFZCWVJAT"


def montar_rotores(escolha: List[int], posições: List[int]) -> List[Rotor]:
    lista = []

    for sel, pos in zip(escolha, posições):
        fiação, entalhe, nome = ROTORES_PADRÃO[sel]
        r = Rotor(fiação=fiação, entalhe=entalhe, nome=nome)
        r.definir_posição(pos)
        lista.append(r)

    return lista


def cifrar_arquivo(rotors_escolhidos, posicoes_iniciais, caminho_txt, saída="saida.txt"):
    rotores = montar_rotores(rotors_escolhidos, posicoes_iniciais)
    refletor = Refletor(REFLETOR_B)
    enigma = MaquinaEnigma(rotores, refletor)

    with open(caminho_txt, "r", encoding="utf-8") as arq:
        texto = arq.read()

    cifrado = enigma.cifrar_mensagem(texto)

    with open(saída, "w", encoding="utf-8") as arq:
        arq.write(cifrado)

    return cifrado


if __name__ == "__main__":
    rotores = [1, 2, 3]     
    posicoes = [1, 1, 1]     
    
    caminho = r"C:\Users\Rodrigo Santana\Documents\desenvolvimento\github\algoritmos\Criptografia\mensagem.txt" 

    testeCollab = """ATAQUE AO AMANHECER
                ENVIAR TROPAS PELO NORTE"""
    
    try:
        resultado = cifrar_arquivo(rotores, posicoes, caminho)
        print("Resultado: ", resultado[:200])

        enigma = MaquinaEnigma(montar_rotores(rotores, posicoes), Refletor(REFLETOR_B))
        resultado = enigma.cifrar_mensagem(testeCollab)
        print("Resultado: ", resultado[:200])
    
    except FileNotFoundError:
        print("Arquivo não encontrado.")
//
// Calcula a tabela-verdade para uma fórmula fixa
// Codigo Modificado por: Fabio Melo e Luigge Lena
//

#include <stdio.h>

#define TRUE              1
#define FALSE             0

// macro para calcular o valor da implicacao
#define IMP(b1, b2)       (b1 && !b2 ? FALSE : TRUE)

//
// A fórmula calculada pelo codigo e'
// ((P \/ R) /\ ~S) -> (P \/ Q)
//

// numero de variaveis proposicionais na formula
#define VARS              4

// constantes para os indices das variaveis
#define P                 0
#define Q                 1
#define R                 2
#define S                 3
//adicionado variavel S.

// representacao da formula
char nome[VARS];     // nome das variaveis
int I[VARS];         // interpretacao das variaveis

void inicializa_formula()
{
  nome[P] = 'P';
  nome[Q] = 'Q';
  nome[R] = 'R';
  nome[S] = 'S';

  for (int c = 0; c < VARS; c++)
    I[c] = FALSE;
}

// retorna TRUE se a interpretacao atual eh a ultima na tabela-verdade
int ultima_interpretacao()
{
  int res = 1;

  for (int c = 0; c < VARS; c++) {
    res = res && I[c];
  }

  return res;
}

// altera a interpretacao atual no array I[] para a proxima na
// ordem da tabela-verdade
void proxima_interpretacao()
{
  int c = VARS - 1;

  while (c >= 0 && I[c] != 0) {
    I[c--] = 0;
  }

  if (c >= 0)
    I[c] = 1;
}

int valor_formula()
{
// para que o codigo funcione, temos que calcular os termos primeiro //
  int calculo1 = I[P] || I[R] && !I[S];
  int calculo2 = I[P] || I[Q];
  return IMP(calculo1,calculo2);
}

void mostra_tabela()
{
  int fim = FALSE;

  inicializa_formula();

  printf("Formula:\n");
  printf("H = ((P \\/ R) /\\ ~S) -> (P \\/ Q)\n\n");

  for (int c = 0; c < VARS; c++) {
    printf(" %c |", nome[c]);
  }
  printf(" H\n");

  for (int c = 0; c < 4 * VARS + 3; c++)
    printf("-");
  printf("\n");

  while (!fim) {
    // imprime valores atuais das variaveis
    for (int c = 0; c < VARS; c++) {
      if (I[c])
        printf(" T |");
      else
        printf(" F |");
    }

    // calcula e imprime o valor da formula
    if (valor_formula())
      printf(" T\n");
    else
      printf(" F\n");

    // verifica se acabou a tabela ou passa para
    // a proxima linha
    if (ultima_interpretacao())
      fim = TRUE;
    else
      proxima_interpretacao();
  }
}

int main()
{
  printf("Calculo de tabela-verdade\n\n");

  mostra_tabela();

  return 0;
}

/* Projeto 3 - Lógica Aplicada A Computação
Alunos: Fábio Alexandre, Luigge Lena, Natália Barreto.

Nomes Aleatórios Gerados em:
http://mcdemarco.net/tools/family-tree-generator/lineage.html#
com o seed: logica-comp-projeto-fabio-natalia-luigge
*/


/* Base de Dados */

homem(arthur).
homem(jason).
homem(paul).
homem(eddie).
homem(rodney).
homem(stanley).
homem(brandon).
homem(curtis).
homem(harry).
homem(timothy).
homem(kevin).

mulher(joyce).
mulher(kathryn).
mulher(rhonda).
mulher(rose).
mulher(judy).
mulher(judith).

/* antecessores */
/* 1a geração */
antecessor(arthur,jason).
antecessor(joyce,jason).
antecessor(arthur,kathryn).
antecessor(joyce,kathryn).
/* 2a geração */
antecessor(paul,rhonda).
antecessor(kathryn,rhonda).
antecessor(brandon,rose).
antecessor(kathryn,rose).
antecessor(brandon,judith).
antecessor(kathryn,judith).
/* 3a geração */
antecessor(rhonda,rodney).
antecessor(eddie,rodney).
antecessor(rhonda,stanley).
antecessor(eddie,stanley).
antecessor(rose,harry).
antecessor(curtis,harry).
antecessor(rose,judy).
antecessor(curtis,judy).

/* Regras dos Predicados */
/* define se o antecessor é pai ou mãe */
pai(X,Y):- homem(Y),antecessor(Y,X).
mae(X,Y):- mulher(Y),antecessor(Y,X).
/* define os filhos(as) */
filho(X,Y):- homem(Y),antecessor(X,Y).
filha(X,Y):- mulher(Y),antecessor(X,Y).

/* irmãos - verifica se irmão A e B tem os mesmos pais, e que sejam diferentes
 deles mesmos */
irmaos(A,B):- pai(A, P),pai(B,P),mae(A,M),mae(B,M),not(A = B).
/* tio(a) - verifica se tal pessoa é irmão(ã) do antecessor */
tio(A,T):- homem(T),antecessor(X,A),irmaos(X,T).
tia(A,T):- mulher(T),antecessor(X,A),irmaos(X,T).
 
/* primo - verifica se um antecessor comum possui filhos */
primo(A,B):- antecessor(A,X),antecessor(B,Y),antecessor(Y,X). 
 
/*avôs(avohomem) e avós(avomulher) - vasculha a árvore genealógica por um antecessor duas gerações acima*/
avohomem(A,B):- pai(A,X),mae(X,B).
avohomem(A,B):- pai(A,X),pai(X,B).
avomulher(A,B):- mae(A,X),pai(X,B).
avomulher(A,B):- mae(A,X),mae(X,B).

/* neto - verifica duas gerações abaixo */
neto(A,B):- homem(A),avohomem(B,A).
neto(A,B):- homem(A),avohomem(B,A).
neta(A,B):- mulher(A),avomulher(B,A).
neta(A,B):- mulher(A),avomulher(B,A).


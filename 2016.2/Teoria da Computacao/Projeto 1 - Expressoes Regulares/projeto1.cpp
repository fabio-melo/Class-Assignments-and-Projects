#include <iostream>
#include <string>
#include <regex>

void verifica_CPF();
void verifica_numeroTelefone();
void verifica_placaCarro();

void menu(){
	cout<< "\n\nEscolha o que deseja fazer\n";
	cout<< "1 - Verificar CPF\n";
	cout<< "2 - Verificar Numero de Telefone\n";
	cout<< "3 - Verificar Placa de Carro \n"
	cout<< "4 - Sair\n";
	cout<< "Sua Escolha: "

int main(int argc, char const *argv[])
{
	int op;
	while(true){
		menu();
		cin>>op;
		switch(op){
			case '1': 
				verifica_CPF();
				break;
			case '2':
				verifica_numeroTelefone();
				break;
			case '3': 
				verifica_placaCarro();
				break;
			case '4': 
				exit(0);
			default:
				cout<<"Seleção Invalida\n";
		}}

	return 0;
}


void verifica_CPF(){
	string cpf;
	regex regex_cpf = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}"
	cout<<"Digite o CPF para verificação:"
	cin>>cpf;
	regex_match()
}

//[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2}
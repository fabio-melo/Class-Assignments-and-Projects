#include <iostream>
#include <string>
#include <regex>
#include <vector>

using namespace std;



void menu(){
	cout<< "\nEscolha o que deseja fazer\n";
	cout<< "1 - Verificar CPF\n";
	cout<< "2 - Verificar Numero de Telefone\n";
	cout<< "3 - Verificar Placa de Carro \n";
    cout<< "4 - Verificar Texto Digitado\n";
	cout<< "5 - Sair\n";
	cout<< "Sua Escolha: ";
}

void verifica_CPF(){
	string cpf;
	regex regex_cpf("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}");
	cout<<"Digite o Numero do CPF:";
	cin>>cpf;
	if(regex_match(cpf, regex_cpf)){
        cout<< "Formato de CPF VALIDO\n";}
    else cout<< "Formato de CPF INVALIDO\n";

}
void verifica_numeroTelefone(){
    string telefone;
    regex regex_telefone("[0-9]{4,5}-[0-9]{4}");
    cout<<"Digite o Numero do Telefone\n";
    cin>>telefone;
    if(regex_match(telefone,regex_telefone)){
        cout<< "Formato de Telefone VALIDO\n";
    }else cout<< "Formato de Telefone INVALIDO\n";
}

void verifica_placaCarro(){
    string placa;
    regex regex_placa("[A-Z]{3}-[0-9]{4}");
    cout<< "Digite a Placa do Automovel\n";
    cin>>placa;
    if(regex_match(placa,regex_placa)){
        cout<< "Formato de Placa VALIDO\n";}
        else cout<< "Formato de Placa INVALIDO\n";
    }

void verificar_texto(){

    vector<string> texto;
    string temp;

    regex regex_cpf("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}");
    regex regex_telefone("[0-9]{4,5}-[0-9]{4}");
    regex regex_placa("[A-Z]{3}-[0-9]{4}");

    smatch match;

    cout<< "Digite o texto que deve procurar, -q para terminar a entrada\n";

    while(true){
        cin>> temp;
        texto.push_back(temp);
        if(temp == "-q"){
            break;
        }
    }

     for (const auto& linha : texto) {
        if(std::regex_search(linha, match, regex_cpf)) {
            for (size_t i = 0; i < match.size(); ++i)
                std::cout << "CPF:" << match[i] << '\n';
        }
        else if(std::regex_search(linha, match, regex_telefone)) {
            for (size_t i = 0; i < match.size(); ++i)
                std::cout << "Telefone:" << match[i] << '\n';
        }
        else if(std::regex_search(linha, match, regex_placa)) {
            for (size_t i = 0; i < match.size(); ++i)
                std::cout << "Placa:" << match[i] << '\n';
        }

    }
}



int main(int argc, char const *argv[])
{
    int op;
	while(true){
		menu();
		cin>>op;
		switch(op){
			case 1:
				verifica_CPF();
				break;
			case 2:
				verifica_numeroTelefone();
				break;
			case 3:
				verifica_placaCarro();
				break;
            case 4:
                verificar_texto();
                break;
			case 5:
				exit(0);
			default:
				cout<<"Opcao Invalida\n";
		}}

	return 0;
}

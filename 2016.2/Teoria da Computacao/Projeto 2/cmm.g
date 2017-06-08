grammar cmm;

start_rule
	:	 programa;

programa:	funcao+;

tipo 	:	'int'
	|	'void'
	;

param	:	tipo ID;
	
params	:	param (',' param)*;
	
funcao	:	tipo ID '(' params? ')' '{' comando* '}';

comando	:	ID '=' exp ';'                           // atribuicao
	|	'return' exp ';'                         // return
	|	'if' '(' exp ')' comando ('else' comando)*  // condicional
	|	'{' comando+ '}'                         // bloco de comandos
	|	ID '(' params? ')'  ';'  // chamada de funcao
	;

exp 	:	expLog;

expLog 	:	expRel ('||' expRel | '&&' expRel)*
	;
expRel	:	expSoma ('<' expSoma | '==' expSoma)*
	;

expSoma	:	expMul ('+' expMul)*
	;
	
expMul	:	fator ('*' fator)*
	;
	
fator 	:	ID                // variavel
	|	INT               // literal inteiro
	|	ID '(' args? ')'  // chamada de funcao
	;

args	:	exp (',' exp)*;

// tokens
ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT :	'0'..'9'+
    ;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;


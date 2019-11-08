// これがパーサー（構文解析）だということを示す
parser grammar PreProssesorParser;

options{
    tokenVocab=PreProssesorLexer;
}

root :
	statement* EOF
	;
statement :
	SHARP DEFINE STATE_END
	;
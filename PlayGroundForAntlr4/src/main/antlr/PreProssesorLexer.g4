// これがレキサー（字句解析）だということを示す
lexer grammar PreProssesorLexer;

SHARP : '#';
DEFINE: 'define';
// 文の終わり
STATE_END : '\n';

// 無視するやつ
WHITESPACE : (' ' | '\t' | '\r' )+ -> skip;


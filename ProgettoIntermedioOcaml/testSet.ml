(* TEST FUNZIONALITA' *)

let env0 = emptyEnv;;   (* creo l'ambiente *)

let f1 = Fun("x", Op(Ide "x",Mul,EInt 3));;  (* provo una chiamata generica di funzione f(x) = x*3 *)
let val1 = FunCall(f1,EInt 5);;   (* ritorna 15 *)
eval val1 env0;;

(* provo una generica funzione ricorsiva f(x) = 1+2+...x *)
let sumTo = RecFun("sum","x",IfThenElse(EqOp(Ide "x",Equal,EInt 1), EInt 1, Op(Ide "x",Sum, FunCall(Ide "sum",Op(Ide "x",Sub,EInt 1)))));;
let val2 = FunCall(sumTo,EInt 4);;  (* ritorna 10 *)
eval val2 env0;;

(* testo il funzionamento delle funzioni a 2 parametri con un semplice confronto x >= y *)
let f2 = FunTwo("x","y",EqOp(Ide "x",GEq,Ide "y"));;
let val3 = FunCallTwo(f2,EInt 5,EInt 3);;   (* ritorna true *)
let val4 = FunCallTwo(f2,EInt 4,EInt 12);;  (* ritorna false *)
eval val3 env0;;
eval val4 env0;;

let myDict = EDictionary ([("Alpha",EInt 1);("Beta",EInt 2);("Gamma",EInt 3)]);;  (* creazione dizionario con 3 elementi *)
eval myDict env0;;

let myDict1 = Insert("Delta",EInt 4,myDict);;  (* inserisco una nuova coppia *)
eval myDict1 env0;;

let plus1 = Fun("x",Op(Ide "x",Sum,EInt 1));;    (* applico la funzione plus1 a tutti gli elementi *)
let dictPlus1 = Iterate(plus1,myDict);;
eval dictPlus1 env0;;

let plus1Acc = FunTwo("x","acc",Op(Ide "acc",Sum,Op(Ide "x",Sum,EInt 1)));;  (* chiamo la fold sul dizionario con la funzione plus1Acc *)
let foldTest = Fold(plus1Acc,myDict);;
eval foldTest env0;;

let checkAlpha = HasKey("Alpha",myDict);;   (* cerco una chiave esistente *)
eval checkAlpha env0;;

let checkArance = HasKey("Arance",myDict);;   (* cerco una chiave che non è presente, mi aspetto l'errore *)
eval checkArance env0;;

let myDict2 = Delete("Gamma",myDict);;  (* cancello una coppia identificata dalla chiave *)
eval myDict2 env0;;

let subDict = Filter(["Beta";"Delta"],myDict);;  (* filtro il dizionario prendendo solo due coppie *)
eval subDict env0;;

let myDict3 = Insert("Pi",EBool false,myDict);; (* inserisco un boolean in un dizionario di interi, si crea un errore *)
eval myDict3  env0;;

let emptyDict = EDictionary ([]);;
let foldOnEmpty = Fold(plus1Acc,emptyDict);;  (* provo la Fold su un dizionario vuoto, l'operazione fallisce *)
eval foldOnEmpty env0;;

let error01 = Delete("Eta",myDict);;  (* cancello una coppia identificata dalla chiave *)
eval error01 env0;;

let error02 = Filter(["Phi";"Sigma"],myDict);; (* filtro con dur valori non presenti,ottengo lista vuota *)
eval error02 env0;;

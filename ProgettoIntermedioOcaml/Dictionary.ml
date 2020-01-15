type ide = string;;

type op = Sum | Sub | Mul;;

type bool_op = And | Or;;

type eq_op = Equal | Greater | Lower | GEq | LEq;;

(* aggiungo le nuove funzionalità all'interprete base *)

type exp =
    | EInt of int
    | EBool of bool
    | Not of exp
    | Ide of ide
    | Op of exp * op * exp
    | BoolOp of exp * bool_op * exp
    | EqOp of exp * eq_op * exp
    | EDictionary of (ide * exp) list
    | Insert of ide * exp * exp
    | Delete of ide * exp
    | HasKey of ide * exp
    | Iterate of exp * exp
    | Filter of (ide list) * exp
    | Fold of exp * exp
    | IfThenElse of exp * exp * exp
    | Let of ide * exp * exp
    | Fun of ide * exp
    | RecFun of ide * ide * exp
    | FunCall of exp * exp
    | FunTwo of ide * ide * exp
    | FunCallTwo of exp * exp * exp;;    (* 31-32 estensione per funzioni con 2 parametri *)

(* tipi riconosciuti *)

type evT =
    | Int of int
    | Bool of bool
    | String of string
    | Dictionary of (ide * evT) list
    | FunClosure of ide * exp * env
    | RecClosure of ide * ide * exp * env
    | FunTwoClosure of ide * ide * exp * env   (* per gestire le funzioni con due parametri *)
    | Unbound
and env = (ide * evT) list;;

let emptyEnv = [("",Unbound)];;

let bind (var : ide) (v : evT) (e : env) : env = (var, v) :: e;;

(* binding per funzioni con accumulatore *)
let bindTwo (x : ide) (y : ide) (v1 : evT) (v2 : evT) (e : env) : env =
    (x, v1) :: (y, v2) :: e;;

let rec lookup (id : ide) (r : env) : evT = match r with
    | [] -> Unbound
    | (x, value) :: l -> if(id = x) then value else lookup id l;;

let typecheck (s : string) (e : evT) : bool = match s with
    | "int" -> (match e with
             Int(_) -> true
             | _ -> false)
    | "bool" -> (match e with
              Bool(_) -> true
              | _ -> false)
    | "string" -> (match e with
              String(_) -> true
              | _ -> false)
    | "dizionario" -> (match e with       (* aggiungo il tipo dizionario al typechecking dinamico *)
              Dictionary(_) -> true
              | _ -> false)
    | _ -> failwith("Unknown Type");;

let eval_not (e : evT) : evT = match e with
         | Bool(true) -> Bool(false)
         | Bool(false) -> Bool(true)
         | _ -> failwith("Invalid Type(required boolean)");;

(* un "typecheking interno" che controlla il tipo di valori nel dizionario *)
let dicType (d : (ide * evT) list) : string = match d with
    | [] -> failwith("Something gone wrong!")
    | (key, info) :: xs -> (match info with
                            | Int(_) -> "int"
                            | Bool(_) -> "bool"
                            | String(_) -> "string"
                            | _ -> failwith("Type Not Allowed"));;

let eval_op (e1 : evT) (o : op) (e2 : evT) : evT = match e1, o, e2 with
    | Int(x), op, Int(y) -> (match op with
                            |Sum -> Int(x+y)
                            |Sub -> Int(x-y)
                            |Mul -> Int(x*y))
    | _ -> failwith("Invalid Type");;

let eval_bool (a : evT) (o : bool_op) (b : evT) : evT = match a, o, b with
    | Bool(a), op, Bool(b) -> (match op with
                               |And -> Bool(a && b)
                               |Or -> Bool(a || b))
    | _ -> failwith("Invalid Type");;

let eval_eq (x : evT) (o : eq_op) (y : evT) : evT = match x, o, y with
    | Bool(x), op, Bool(y) -> (match op with
                               Equal -> if(x = y) then Bool(true)
                                        else Bool(false)
                               | _ -> failwith("Invalid Operation for boolean"))
    | Int(x), op, Int(y) -> (match op with
                             Equal -> if(x = y) then Bool(true)
                                      else Bool(false)
                             | Greater -> if(x > y) then Bool(true)
                                        else Bool(false)
                             | Lower -> if(x < y) then Bool(true)
                                      else Bool(false)
                             | GEq -> if (x >= y) then Bool(true)
                                    else Bool(false)
                             | LEq -> if (x <= y) then Bool(true)
                                    else Bool(false))
    | _ -> failwith("Invalid Type");;

(* controlla se una chiave è presente in un dizionario *)
let rec memberDict (d: (ide * evT) list) (k: ide) = match d with
    | []-> false
    | (key,info) :: xs -> if (key=k) then true else memberDict xs k;; (*true else memberDict xs k*);;

(* controlla se un elemento è presente in una lista *)
let rec member n l = match l with
    | [] -> false
    | x :: xs -> if(x = n) then true else member n xs;;

(* controlla che il dato inserito sia conforme con il tipo del dizionario *)
let evInsert (x: ide) (v: evT) (d: (ide * evT) list): (ide * evT) list  = match d with
    [] -> [ (x,v) ]
    | (key,info) :: xs -> if  (typecheck (dicType d) v && (memberDict d x) = false)
                                  then (x,v)::d
                          else failwith("Dinzionario non omogeneo");;

let rec evDelete (x:ide) (d: (ide * evT) list) = match d with
    [] -> failwith("Key not found")
    | (key,info) :: xs -> if (key=x) then xs
                          else (key,info) :: evDelete x xs;;

let rec evFilter (x: ide list) (d: (ide * evT) list) : (ide * evT) list = match d with
    [] -> []
    | (key,info) :: xs -> if (member key x) then (key,info) :: evFilter x xs
                         else evFilter x xs;;

(* controlla se un intero dizionario è omogeneo *)
let rec checkOver ( d: (ide * evT) list) = match d with
    [] -> true
    | (key,info)::[] -> true
    | (key1,info1)::(key2,info2)::xs -> ( match info1, info2 with
                                        | Int(_),Int(_) -> checkOver ( (key2,info2) :: xs)
                                        | String(_),String(_) -> checkOver ( (key2,info2) :: xs)
                                        | Bool(_),Bool(_) -> checkOver ( (key2,info2) :: xs)
                                        | _,_ -> false) ;;

let rec eval (e : exp) (r : env) : evT = match e with
    | EInt(x) -> Int(x)
    | EBool(b) -> Bool(b)
    | Not(b) -> eval_not (eval b r)
    | Ide(x) -> lookup x r
    | Op(x, o, y) -> eval_op (eval x r) o (eval y r)
    | BoolOp(a, o, b) -> eval_bool (eval a r) o (eval b r)
    | EqOp(x, o, y) -> eval_eq (eval x r) o (eval y r)
    | EDictionary(lst) ->let auxDict= Dictionary(dictEv lst r) in
                            (match auxDict with
                            |  Dictionary(l) -> if ( checkOver l) then auxDict
                                               else failwith ("Dizionario non omogeneo")
                            |_ -> failwith("Something gone wrong!"))
    | Insert(k, v, d) -> let tmp = eval d r in
                               let tmpVal = eval v r in
                                 (match tmp with
                                 | Dictionary(l) -> Dictionary(evInsert k tmpVal l)
                                 | _ -> failwith("Type Error"))    (* se l non è un dizionario *)
    | IfThenElse(cond, a, b) -> (match eval cond r with
                                   | Bool(true) -> eval a r
                                   | Bool(false) -> eval b r
                                   | _ -> failwith("Non-Boolean Condition"))
    | Delete(k, d) -> let tmp = eval d r in
                             (match tmp with
                               | Dictionary(l) -> Dictionary(evDelete k l)
                               | _ -> failwith("Type Error"))
    | Filter(k, d) -> let tmp = eval d r in
                         (match tmp with
                          | Dictionary(l) -> Dictionary(evFilter k l)
                          | _ -> failwith("Type Error"))
    | HasKey(k, d) -> let tmp = eval d r in
                           (match tmp with
                           | Dictionary(l) -> if(memberDict l k) then Bool(true)
                                        else Bool(false)
                           | _ -> failwith("Type Error"))
    | Let(id, e1, e2) -> eval e2 (bind id (eval e1 r) r)
    | Fun(param, body) -> FunClosure(param, body, r)
    | RecFun(id, param, body) -> RecClosure(id, param, body, r)
    | FunCall(f, e) -> let evalue = eval e r in
                           let fclose = eval f r in
                               (match fclose with
                                | FunClosure(idparam, body, env) ->
                                    eval body (bind idparam evalue env)
                                | RecClosure(fid, idparam, body, env) ->
                                    let recEnv = bind fid fclose env in
                                        eval body (bind idparam evalue recEnv)
                                | _ -> failwith("Invalid function"))
    | FunTwo(par, acc, body) -> FunTwoClosure(par, acc, body, r)  (* funzione che prende due parametri *)
    | FunCallTwo(f, e, a) -> let evalue = eval e r in
                                let avalue = eval a r in
                                  let fclose = eval f r in
                                  (match fclose with
                                    | FunTwoClosure(par, acc, body, env) ->
                                       eval body (bindTwo par acc evalue avalue env)
                                    | _ -> failwith("Invalid function"))
    | Fold(f, d) -> let a = EInt(0) in
                          (match d, f with
                            | EDictionary(l), FunTwo(par, acc, body) -> applyAll l f a r
                            | _ -> failwith("Type Error"))
    | Iterate(f, d) ->  (match d, f with
                             | EDictionary(l), Fun(param, body) -> Dictionary(iterateEv l f r)
                             | EDictionary(l), RecFun(id, param, body) -> Dictionary(iterateEv l f r)
                             | _ -> failwith("Type Error"))

(* mi restistuisce il dizionaro quando chiamo eval su un oggetto di tipo Dictionary *)
and dictEv (lst: (ide * exp) list ) (r: env) : (ide * evT) list = match lst with
    [] -> []
    | (key,info) :: xs -> (key, eval info r) :: dictEv xs r

(* applica la funzione a tutti gli elementi del dizionario *)
and iterateEv (lst : (ide * exp) list) (f : exp) (r : env) : (ide * evT) list = match lst with
    [] -> []
    | (key,info) :: xs -> let fCall = FunCall(f, info) in
                              (key, eval fCall r) :: iterateEv xs f r

(* applica la funzione con accumulatore a tutti gli elementi del dizionario *)
and applyAll (lst : (ide * exp) list) (f : exp) (acc : exp) (r : env) : evT = match lst with
        | [] -> failwith("Empty Dictionary")
        | (key, info) :: [] -> let final = FunCallTwo(f, info, acc) in
                                    eval final r
        | (key, info) :: xs -> applyAll xs f (FunCallTwo(f, info, acc)) r ;;

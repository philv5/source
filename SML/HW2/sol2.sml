(* Problem1 *)
datatype expr = NUM of int
	| PLUS of expr * expr
	| MINUS of expr * expr

datatype formula = TRUE
	| FALSE
	| NOT of formula
	| ANDALSO of formula * formula
	| ORELSE of formula * formula
	| IMPLY of formula * formula
	| LESS of expr * expr

fun getValue(e) =
	case e of
		NUM i => i
	|	PLUS(e1,e2) => getValue(e1) + getValue(e2)
	|	MINUS(e1,e2) => getValue(e1) - getValue(e2)

fun eval(e) =
	case e of
		TRUE => true
	|	FALSE => false
	|	NOT e1 => not(eval(e1))
	|	ANDALSO(e1,e2) => eval(e1) andalso eval(e2)
	|	ORELSE(e1,e2) => eval(e1) orelse eval(e2)
	|	IMPLY(e1,e2) => if (eval(e1) = true) andalso (eval(e2) = false) then false else true
	|	LESS(e1,e2) => getValue(e1) < getValue(e2)


(* Problem2 *)
type name = string
datatype metro = STATION of name
	| AREA of name * metro
	| CONNECT of metro * metro

fun checkMetro(m) = 
	let
		fun getArea x = 
			case x of
				STATION e1 => []
			|	AREA(e1,e2) => [e1] @ getArea(e2)
			|	CONNECT(e1,e2) => getArea(e1) @ getArea(e2)

		fun getStation x = 
			case x of
				STATION e1 => [e1]
			|	AREA(e1,e2) => getStation(e2)
			|	CONNECT(e1,e2) => getStation(e1) @ getStation(e2)

		fun check(a : string list, s : string list) =
			case (a,s) of
				([],y) => false
			|	(x,[]) => true
			|	(x::xs, y::ys) => if not(null(ys)) then check(x::xs,[y]) andalso check(x::xs,ys)
				  		else if x = y then true
				  		else check(xs,[y])

	in
		check(getArea(m), getStation(m))
	end


(* Problem3 - 1 *)
datatype 'a lazyList = nullList
		     | cons of 'a * (unit -> 'a lazyList)

fun seq(first,last) =
	if first = last then cons(first, fn() => nullList)
	else cons(first, fn() => seq(first+1,last))

fun infSeq(first) =
	cons(first, fn() => infSeq(first+1))

fun firstN(lazyListVal,n) = 
	case (lazyListVal,n) of
		(nullList,_) => []
	|	(_, 0) => []
	|	(cons(x,f),n) => x :: firstN(f(),n-1)

fun Nth(lazyListVal,n) = 
	case (lazyListVal,n) of
		(nullList,_) => NONE
	|	(cons(x,f),1) => SOME x
	|	(cons(x,f),n) => Nth(f(),n-1)

fun filterMultiples(lazyListVal,n) = 
	case (lazyListVal,n) of
		(nullList,_) => nullList
	|	(cons(x,f),n) => if (x mod n) = 0 then filterMultiples(f(),n)
				 else cons(x,fn() => filterMultiples(f(),n))

(* Problem3 - 2 *)
fun primes() = 
	let 
		fun sieve(cons(x,f)) = cons(x, fn() => sieve(filterMultiples(f(),x)))
	in
		sieve(infSeq(2))
	end
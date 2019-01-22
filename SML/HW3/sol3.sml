(* Problem 1 *)
datatype pattern = Wildcard | Variable of string | UnitP
		 | ConstP of int | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int | Unit | Tuple of valu list
	      | Constructor of string * valu

fun check_pat(p) = 
	let
		fun mk_string_list(pat,slist) =
			case pat of
				Variable(s) => slist @ [s]
			|	ConstructorP(s,p) => mk_string_list(p,slist)
			|	TupleP(plist) => foldl mk_string_list slist plist
			|	_ => slist

		fun isDistinct(slist) =
			case slist of
				[] => true
			|	x::[] => true
			|	x1::x2::xs => if(x1 = x2) then false
					      else true andalso isDistinct(xs)
	in
		if(isDistinct(mk_string_list(p,[]))) then true
		else false
	end

(* Problem 2 *)
fun match(v,p) = 
	let 

	fun append_match(x) = 
		case x of
			[] => []
		|	x::xs => if(match(x) = NONE) then append_match(xs) else valOf(match(x)) @ append_match(xs)

	fun check_match(x) = 
		case x of
			[] => true
		|	x::xs => if(match(x) = NONE) then false
				 else check_match(xs)

	in

	case (v,p) of
		(_,Wildcard) => SOME []
	|	(_,Variable(s)) => SOME [(s,v)]
	|	(Unit,UnitP) => SOME []
	|	(Const(numv),ConstP(nump)) => if(numv = nump) then SOME [] else NONE
	|	(Constructor(s1,v0),ConstructorP(s2,p0)) => if(s1 = s2) then match(v0,p0) else NONE
	|	(Tuple(vs),TupleP(ps)) => if((List.length(vs) = List.length(ps)) andalso check_match(ListPair.zip(vs,ps))) 
					  then SOME(append_match(ListPair.zip(vs,ps)))
					  else NONE
	|	_ => NONE
	
	end

(* Problem 3 *)
type name = string

datatype RSP = ROCK | SCISSORS | PAPER

datatype 'a strategy = Cons of 'a * (unit -> 'a strategy)

datatype tournament =
	PLAYER of name * (RSP strategy ref)
    |   MATCH of tournament * tournament

fun onlyOne(one:RSP) = 
	Cons(one, fn() => onlyOne(one))

fun alterTwo(one:RSP, two:RSP) =
	Cons(one, fn() => alterTwo(two,one))

fun alterThree(one:RSP, two:RSP, three:RSP) =
	Cons(one, fn() => alterThree(two,three,one))

val r = onlyOne(ROCK)
val s = onlyOne(SCISSORS)
val p = onlyOne(PAPER)
val rp = alterTwo(ROCK,PAPER)
val sr = alterTwo(SCISSORS,ROCK)
val ps = alterTwo(PAPER,SCISSORS)
val rs = alterTwo(ROCK,SCISSORS)
val srp = alterThree(SCISSORS,ROCK,PAPER)

fun next(strategyRef) =
	let 
		val Cons(rsp, func) = !strategyRef
	in
		(strategyRef := func();rsp)
	end


fun whosWinner(t) = 
	let
	
	fun match_game(p_t1,p_t2) =
		case (p_t1,p_t2) of
			(PLAYER(a,b),MATCH(t1,t2)) => match_game(PLAYER(a,b), match_game(t1,t2))
		|	(MATCH(t1,t2),PLAYER(a,b)) => match_game(PLAYER(a,b), match_game(t1,t2))
		|	(MATCH(t1,t2),MATCH(t3,t4)) => match_game(match_game(t1,t2), match_game(t3,t4))
		|	(PLAYER(name1,strategyRef1),PLAYER(name2,strategyRef2)) => 
				let 
					val s1 = next(strategyRef1)
					val s2 = next(strategyRef2)
				in
					if(s1 = s2) then match_game(PLAYER(name1,strategyRef1),PLAYER(name2,strategyRef2))
					else if((s1 = ROCK andalso s2 = SCISSORS) orelse (s1 = SCISSORS andalso s2 = PAPER) orelse (s1 = PAPER andalso s2 = ROCK))
					then PLAYER(name1,strategyRef1)
					else PLAYER(name2,strategyRef2)
				end
	
	in

	case t of
		MATCH(t1,t2) => match_game(t1,t2)
	|	_ => t

	end


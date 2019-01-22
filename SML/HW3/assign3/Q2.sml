Control.Print.out := {say=fn _=>(), flush=fn()=>()};

use "sol3.sml";

fun assert(result, expected) =
    if result = expected then true
    else false
    handle _ => false  (* This is exception handler.
                        * Exception is considered as test failure *)

val test = assert(match(Const(0), Wildcard), SOME []) andalso
           assert(match(Constructor("a", Unit), ConstructorP("b", UnitP)), NONE) andalso
           assert(match(Tuple([Const(1), Unit, Tuple([]), Constructor("a", Unit)]), TupleP([ConstP(1), UnitP, TupleP([]), ConstructorP("b", UnitP)])), NONE)
           handle _ => false

val _ = print(if test then "GOOD" else "BAD");

val _ = OS.Process.exit(OS.Process.success);

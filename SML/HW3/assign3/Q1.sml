Control.Print.out := {say=fn _=>(), flush=fn()=>()};

use "sol3.sml";

fun assert(result, expected) =
    if result = expected then true
    else false
    handle _ => false  (* This is exception handler.
                        * Exception is considered as test failure *)

val test = assert(check_pat(Wildcard), true) andalso
           assert(check_pat(TupleP([ConstructorP("a", TupleP([Variable("a"), Variable("b")])), Variable("c")])), true) andalso
           assert(check_pat(TupleP([Variable("a"), ConstructorP("b", TupleP([Variable("b"), Variable("c")])), Variable("c")])), false)
           handle _ => false

val _ = print(if test then "GOOD" else "BAD");

val _ = OS.Process.exit(OS.Process.success);

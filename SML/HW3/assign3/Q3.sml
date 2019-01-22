Control.Print.out := {say=fn _=>(), flush=fn()=>()};


use "sol3.sml";

val match1 = MATCH(MATCH(PLAYER("John", ref r), PLAYER("Steve", ref p)), 
              PLAYER("Alice", ref s))

val match2 = MATCH(MATCH(PLAYER("John", ref rs), PLAYER("Steve", ref s)),
                  MATCH(PLAYER("Alice", ref p),
                        MATCH(PLAYER("David", ref r),
                              MATCH(PLAYER("Bill", ref s), PLAYER("Emily", ref srp)))))
fun get(t) = 
  case t of
       PLAYER(n, s) => n


fun assert(result, expected) =
    if result = expected then true
    else false
    handle _ => false  (* This is exception handler.
                        * Exception is considered as test failure *)

val test = assert(get(whosWinner(match1)), "Alice") andalso
           assert(get(whosWinner(match2)), "Emily")
           handle _ => false

val _ = print(if test then "GOOD" else "BAD");

val _ = OS.Process.exit(OS.Process.success);

; EXPECT: unsat
;; unsupported operator int.pow2
; DISABLE-TESTER: alethe
(set-logic QF_NIA)
(declare-fun x () Int)
(assert (< x 0))
(assert (distinct (int.pow2 x) 0))
(check-sat)

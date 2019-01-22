#lang racket
(provide (all-defined-out))

(define (dataOf node) (car node))
(define (leftChildOf node) (cadr node))
(define (rightChildOf node) (caddr node))

(define (check_bst root)
  (if (null? root)
      #t
      (letrec ([data (dataOf root)]
            [checkL (lambda(node)
                      (if (null? node) #t
                          (> data (dataOf node))))]
            [checkR (lambda(node)
                      (if (null? node) #t
                          (< data (dataOf node))))])
        (and (checkL (leftChildOf root)) (checkR (rightChildOf root)) (check_bst (leftChildOf root)) (check_bst (rightChildOf root))))))


(define (apply func root)
  (if (null? root) null
      (list (func (dataOf root)) (apply func (leftChildOf root)) (apply func (rightChildOf root)))))


(define (equals root1 root2)
  (letrec ([isExist (lambda(data root)
                   (if (null? root) #f
                   (or (= data (dataOf root)) (isExist data (leftChildOf root)) (isExist data (rightChildOf root)))))]
           [cmp (lambda(rootS rootT)
                 (if (null? rootS) #t
                 (and (isExist (dataOf rootS) rootT) (cmp (leftChildOf rootS) rootT) (cmp (rightChildOf rootS) rootT))))])
    (and (cmp root1 root2) (cmp root2 root1))))
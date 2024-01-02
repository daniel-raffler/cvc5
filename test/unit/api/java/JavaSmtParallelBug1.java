/******************************************************************************
 * Test for JavaSMT bug #310
 * https://github.com/sosy-lab/java-smt/issues/310
 */

package tests;

import io.github.cvc5.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

class JavaSmtParallelBug1 {
  @Test
  public void javaSmtParallelBug1Broken() throws InterruptedException, ExecutionException {
    Solver solver = new Solver();

    ExecutorService exec = Executors.newSingleThreadExecutor();
    Future<?> result =
        exec.submit(
            () -> {
              // FIXME: SEGFAULT (without -ea)
              //  Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
              //  C  [libcvc5.so.1+0x5e44c2]  cvc5::Solver::getBooleanSort() const+0x12
              //  Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)
              //  j  io.github.cvc5.Solver.getBooleanSort(J)J+0
              //  j  io.github.cvc5.Solver.getBooleanSort()Lio/github/cvc5/Sort;+5
              //  j  tests.JavaSmtParallelBug1.lambda$bug1$0(Lio/github/cvc5/Solver;)Ljava/lang/Object;+1

              // FIXME: ABORT (with -ea)
              //  Fatal failure within
              //  static cvc5::internal::TypeNode
              //     cvc5::internal::expr::TypeChecker::computeType(
              //       cvc5::internal::NodeManager*,
              //       cvc5::internal::TNode,
              //       bool,
              //       std::ostream*
              //       )
              //  at /home/daniel/workspace/cvc5/build/src/expr/type_checker.cpp:2828
              //  Unhandled case encountered  VARIABLE
              Sort sortBool = solver.getBooleanSort();
              return null;
            });
    
    assert result.get() == null;
    solver.deletePointer();
  }
}

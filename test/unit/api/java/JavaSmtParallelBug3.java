/******************************************************************************
 * Test for JavaSMT bug #310
 * https://github.com/sosy-lab/java-smt/issues/350
 */

package tests;

import io.github.cvc5.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

class JavaSmtParallelBug3 {
  @Test
  public void javaSmtParallelBug3Broken() throws InterruptedException, ExecutionException {
    Solver solver = new Solver();
    Term varA = solver.mkConst(solver.getBooleanSort(), "a");

    solver.assertFormula(varA);

    ExecutorService exec = Executors.newSingleThreadExecutor();
    Future<?> task1 =
        exec.submit(
            () -> {
              // FIXME: SEGFAULT (without -ea)
              //  Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
              //  C  [libcvc5.so.1+0x5fb5e6]  cvc5::Solver::push(unsigned int) const+0x36
              //  Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)
              //  j  io.github.cvc5.Solver.push(JI)V+0
              //  j  io.github.cvc5.Solver.push(I)V+13
              //  j  io.github.cvc5.Solver.push()V+2

              // FIXME: SIGABRT (with -ea)
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
              solver.push();
              return null;
            });

    assert task1.get() == null;
    solver.deletePointer();
  }
}

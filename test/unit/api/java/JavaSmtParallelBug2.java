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

class JavaSmtParallelBug2 {  
  @Test
  public void javaSmtParallelBug2Broken() throws InterruptedException, ExecutionException {
    Solver solver = new Solver();
    Term formula = solver.mkFalse();

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<?> result =
        executor.submit(
            () -> {
              Solver prover = new Solver();
              
              // FIXME: Exception (only with -ea)
              //  io.github.cvc5.CVC5ApiException:
              //   Given term is not associated with the node manager of this solver
              //  at io.github.cvc5.Solver.assertFormula(Native Method)
              //  at io.github.cvc5.Solver.assertFormula(Solver.java:1511)
              //  at org.sosy_lab.java_smt.solvers.cvc5.CVC5NativeAPITest
              //  at ..here
              prover.assertFormula(formula);

              prover.deletePointer();
              return null;
            });

    assert result.get() == null;
    solver.deletePointer();
  }
}

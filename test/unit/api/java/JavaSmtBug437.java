/******************************************************************************
 * Top contributors (to current version):
 *   Andrew Reynolds, Mudathir Mohamed
 *
 * This file is part of the cvc5 project.
 *
 * Copyright (c) 2009-2023 by the authors listed in the file AUTHORS
 * in the top-level source directory and their institutional affiliations.
 * All rights reserved.  See the file COPYING in the top-level source
 * directory for licensing information.
 * ****************************************************************************
 *
 * Test for JavaSMT bug #347
 * https://github.com/sosy-lab/java-smt/issues/347
 */

package tests;

import io.github.cvc5.*;
import org.junit.jupiter.api.Test;

class JavaSmtBug437 {
  private class Task extends Thread {
    @Override
    public void run() {
      try {
        Solver solver = new Solver();
        Term formula = solver.mkBoolean(false);

        solver.push();
        solver.assertFormula(formula);

        assert !solver.checkSat().isSat();
        
        solver.deletePointer(); // Remove this line to fix

      } catch (CVC5ApiException pE) {
        throw new RuntimeException(pE);
      }
    }
  }

  @Test
  public void bug347BrokenTest() throws InterruptedException {
    for (int k = 0; k < 100; k++) {
      System.out.println(k);
      
      Task t1 = new Task();
      t1.start();

      Task t2 = new Task();
      t2.start();

      t1.join();
      t2.join();
    }
  }
}

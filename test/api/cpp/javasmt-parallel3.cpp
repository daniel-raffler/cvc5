 /******************************************************************************
 * Top contributors (to current version):
 *   Morgan Deters, Andrew Reynolds, Mathias Preiner
 *
 * This file is part of the cvc5 project.
 *
 * Copyright (c) 2009-2023 by the authors listed in the file AUTHORS
 * in the top-level source directory and their institutional affiliations.
 * All rights reserved.  See the file COPYING in the top-level source
 * directory for licensing information.
 * ****************************************************************************
 *
 * Test for JavaSMT bug #310
 * https://github.com/sosy-lab/java-smt/issues/310
 */

#include <cvc5/cvc5.h>
#include <thread>

using namespace cvc5;

void parallel3(Solver& solver) {
  solver.push();
};

int main() {
  Solver solver;
  Term varA = solver.mkConst(solver.getBooleanSort(), "a");
  
  solver.assertFormula(varA);
  
  std::thread task(parallel3, std::ref(solver));
  task.join();
}

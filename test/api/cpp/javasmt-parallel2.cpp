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

void parallel2(Term& formula) {
  Solver prover;
  // FIXME:
  //  terminate called after throwing an instance of 'cvc5::CVC5ApiException'
  //  what():
  //    Given term is not associated with the node manager of this solver
  prover.assertFormula(formula);
};

int main() {
  Solver solver;
  Term formula = solver.mkFalse();
  
  std::thread task(parallel2, std::ref(formula));
  task.join();
}

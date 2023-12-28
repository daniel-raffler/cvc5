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
 * Test for JavaSMT bug #347
 * https://github.com/sosy-lab/java-smt/issues/347
 */

#include <cassert>
#include <cvc5/cvc5.h>
#include <iostream>
#include <thread>

using namespace cvc5;

void task() {
  Solver* solver = new Solver();
  Term formula = solver->mkBoolean(false);
  
  solver->push();
  solver->assertFormula(formula);
  
  assert(!solver->checkSat().isSat());
  
  delete solver;
};
  
int main() {
  for (int k=0; k < 100; k++) {
    std::cout << k << std::endl;

    std::thread t1(task);
    std::thread t2(task);
    
    t1.join();
    t2.join();
  }
}

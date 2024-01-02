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

void parallel1(Solver& solver) {
  // FIXME:
  //  Fatal failure within
  //  void NodeManager::poolRemove(expr::NodeValue*)
  //  at /home/daniel/workspace/cvc5/build/src/expr/node_manager.h:1083
  //
  //  Check failure d_nodeValuePool.find(nv) != d_nodeValuePool.end()
  //  NodeValue is not in the pool!
  solver.getBooleanSort();
};
  
int main() {
  Solver solver;
  
  std::thread task(parallel1, std::ref(solver));
  task.join();
}

package solveur;

import instance.Instance;
import solution.Solution;

public interface Solveur {
     String getNom();

     Solution solve(Instance instance);
}

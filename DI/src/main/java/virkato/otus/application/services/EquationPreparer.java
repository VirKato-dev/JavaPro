package virkato.otus.application.services;

import virkato.otus.application.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}

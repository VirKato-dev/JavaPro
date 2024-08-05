package virkato.otus.application.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import virkato.otus.application.model.DivisionEquation;
import virkato.otus.application.model.Equation;
import virkato.otus.application.model.MultiplicationEquation;
import virkato.otus.application.services.EquationPreparer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PropertySource("classpath:application.yml")
@Component
public class EquationPreparerImpl implements EquationPreparer {

    @Value("${count}")
    public int count;

    @Override
    public List<Equation> prepareEquationsFor(int base) {
        List<Equation> equations = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            MultiplicationEquation multiplicationEquation = new MultiplicationEquation(base, i);
            DivisionEquation divisionEquation = new DivisionEquation(multiplicationEquation.getResult(), base);
            equations.add(multiplicationEquation);
            equations.add(divisionEquation);

        }
        Collections.shuffle(equations);
        return equations;
    }
}

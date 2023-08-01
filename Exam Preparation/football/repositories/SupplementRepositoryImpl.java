package football.repositories;

import football.entities.supplement.Supplement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SupplementRepositoryImpl implements SupplementRepository{

    public SupplementRepositoryImpl() {
        this.supplements = new ArrayList<>();
    }

    private List<Supplement> supplements;


    @Override
    public void add(Supplement supplement) {
        supplements.add(supplement);

    }

    @Override
    public boolean remove(Supplement supplement) {
        return supplements.remove(supplement);
    }

    @Override
    public Supplement findByType(String type) {
        return supplements.stream().filter(s -> s.getClass().getSimpleName().equals(type))
                .findFirst()
                .orElse(null);
    }
}

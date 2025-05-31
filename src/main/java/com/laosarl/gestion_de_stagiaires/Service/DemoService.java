package com.laosarl.gestion_de_stagiaires.Service;

import com.laosarl.gestion_de_stagiaires.Model.Demo;
import com.laosarl.gestion_de_stagiaires.Repository.DemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DemoService {

    private final DemoRepository demoRepository;

    // Create and save a new Demo
    public Demo createDemo(Demo demo) {
        return demoRepository.save(demo);
    }
}

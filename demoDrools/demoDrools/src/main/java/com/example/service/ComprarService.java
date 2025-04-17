package com.example.service;

import com.example.entity.Compra;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprarService {

    @Autowired
    private KieContainer kieContainer;

    public Compra CompraConDescuento(Compra compra) {
        KieSession kieSession = kieContainer.newKieSession("ksession-RGdescuento");
        kieSession.insert(compra);
        kieSession.fireAllRules();
        kieSession.dispose();
        return compra;
    }
}
